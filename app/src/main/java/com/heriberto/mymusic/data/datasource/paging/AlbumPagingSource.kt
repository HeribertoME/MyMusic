package com.heriberto.mymusic.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.mapper.toDomain
import com.heriberto.mymusic.domain.model.Album

class AlbumPagingSource(
    private val artistId: String,
    private val remote: RemoteDataSource,
    private val pageSize: Int,
    private val includeGroups: String = "album,single",
    private val market: String? = null
) : PagingSource<Int, Album>() {

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor)
        return page?.prevKey?.plus(pageSize) ?: page?.nextKey?.minus(pageSize)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> = try {
        val offset = params.key ?: 0
        when (val res = remote.getArtistAlbumsPage(
            artistId,
            limit = pageSize,
            offset = offset,
            includeGroups,
            market
        )) {
            is NetworkResult.Success -> {
                val body = res.data
                val data = body.items.map { it.toDomain() }

                val next = if (offset + body.limit >= body.total) null else offset + body.limit
                val prev = if (offset == 0) null else (offset - body.limit).coerceAtLeast(0)

                LoadResult.Page(
                    data = data,
                    prevKey = prev,
                    nextKey = next
                )
            }

            is NetworkResult.Error -> LoadResult.Error(
                IllegalStateException(res.message ?: "Error al cargar albums", res.cause)
            )
        }
    } catch (t: Throwable) {
        LoadResult.Error(t)
    }
}