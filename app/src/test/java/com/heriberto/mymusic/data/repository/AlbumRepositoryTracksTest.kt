package com.heriberto.mymusic.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.AlbumTracksResponse
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.SimplifiedArtistDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.TrackDto
import kotlinx.coroutines.test.runTest
import org.junit.Test


class AlbumRepositoryTracksTest {

    @Test
    fun `getAlbumTracks concatenates remote pages`() = runTest {
        val remote = object : RemoteDataSource by DummyRemote() {
            override suspend fun getAlbumTracksPage(
                albumId: String,
                limit: Int,
                offset: Int
            ): NetworkResult<AlbumTracksResponse> {
                val total = 70
                val remaining = (total - offset).coerceAtLeast(0)
                val count = remaining.coerceAtMost(limit)
                val items = (0 until count).map { i ->
                    TrackDto(
                        id = "t_${offset + i}",
                        name = "Track ${offset + i}",
                        artists = listOf(SimplifiedArtistDto(id = "ar", name = "Editors"))
                    )
                }
                return NetworkResult.Success(
                    AlbumTracksResponse(
                        href = null,
                        items = items,
                        limit = limit,
                        next = null,
                        offset = offset,
                        previous = null,
                        total = total
                    )
                )
            }
        }

        val repo = AlbumRepositoryImpl(remote)

        repo.getAlbumTracks("album1").test {
            val list = awaitItem()
            assertThat(list).hasSize(70)
            assertThat(list.first().name).isEqualTo("Track 0")
            assertThat(list.last().name).isEqualTo("Track 69")
            awaitComplete()
        }
    }

}