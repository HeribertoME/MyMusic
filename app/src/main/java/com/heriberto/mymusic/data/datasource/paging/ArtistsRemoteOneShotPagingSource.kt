package com.heriberto.mymusic.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.SpotifyArtistIds
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.domain.model.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistsRemoteOneShotPagingSource(
    private val remote: RemoteDataSource,
    private val pageSize: Int,
) : PagingSource<Int, Artist>() {
    private var cache: List<Artist>? = null

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor)
        return page?.prevKey?.plus(pageSize) ?: page?.nextKey?.minus(pageSize)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val offset = params.key ?: 0

            // Trae remoto solo una vez
            if (cache == null) {
                val result = withContext(Dispatchers.IO) {
                    remote.getFixedArtists(SpotifyArtistIds.ids)
                }
                when (result) {
                    is NetworkResult.Success -> cache = result.data
                    is NetworkResult.Error -> {
                        val ex = IllegalStateException(result.message ?: "Error al cargar artistas (${result.code})", result.cause)
                        return LoadResult.Error(ex)
                    }
                }
            }

            val all = cache.orEmpty()
            val slice = all.drop(offset).take(pageSize)
            val prev = if (offset == 0) null else (offset - pageSize).coerceAtLeast(0)
            val next = if (offset + pageSize >= all.size) null else offset + pageSize

            LoadResult.Page(
                data = slice,
                prevKey = prev,
                nextKey = next
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}