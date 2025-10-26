package com.heriberto.mymusic.data.repository

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.data.datasource.paging.AlbumPagingSource
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.ImageDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.ArtistAlbumsResponse
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.SimplifiedAlbumDto
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AlbumPagingSourceTest {

    private fun page(
        offset: Int,
        limit: Int,
        total: Int,
        count: Int
    ): ArtistAlbumsResponse {
        val items = (0 until count).map { i ->
            SimplifiedAlbumDto(
                albumType = "album",
                totalTracks = 10,
                id = "al_${offset + i}",
                name = "A${offset + i}",
                images = listOf(ImageDto("u", 300, 300)),
                releaseDate = "2020-01-01",
                releaseDatePrecision = "day",
                albumGroup = null
            )
        }
        return ArtistAlbumsResponse(
            href = null,
            items = items,
            limit = limit,
            next = null,
            offset = offset,
            previous = null,
            total = total
        )
    }

    @Test
    fun `load first page returns nextKey`() = runTest {
        val remote = object : RemoteDataSource by DummyRemote() {
            override suspend fun getArtistAlbumsPage(
                artistId: String,
                limit: Int,
                offset: Int,
                includeGroups: String,
                market: String?
            ): NetworkResult<ArtistAlbumsResponse> =
                NetworkResult.Success(page(offset, limit, 45, limit))
        }

        val src = AlbumPagingSource(
            artistId = "x",
            remote = remote,
            pageSize = 20
        )

        val result = src.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        ) as PagingSource.LoadResult.Page

        assertThat(result.data).hasSize(20)
        assertThat(result.prevKey).isNull()
        assertThat(result.nextKey).isEqualTo(20)
    }

    @Test
    fun `load last page returns nexKey null`() = runTest {
        val remote = object : RemoteDataSource by DummyRemote() {
            override suspend fun getArtistAlbumsPage(
                artistId: String, limit: Int, offset: Int, includeGroups: String, market: String?
            ) = NetworkResult.Success(page(offset, limit, total = 40, count = 0.coerceAtLeast(limit - (offset % limit))))
        }

        val src = AlbumPagingSource("x", remote, pageSize = 20)

        val result = src.load(
            PagingSource.LoadParams.Append(key = 40, loadSize = 20, placeholdersEnabled = false)
        ) as PagingSource.LoadResult.Page

        assertThat(result.nextKey).isNull()
        assertThat(result.prevKey).isEqualTo(20)
    }
}