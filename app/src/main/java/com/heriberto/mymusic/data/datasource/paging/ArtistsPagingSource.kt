package com.heriberto.mymusic.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.heriberto.mymusic.data.datasource.local.LocalDataSource
import com.heriberto.mymusic.domain.model.Artist

class ArtistsPagingSource(
    private val local: LocalDataSource,
    private val pageSize: Int
) : PagingSource<Int, Artist>() {

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        val anchor = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchor)
        return anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> = try {
        val offset = params.key ?: 0
        val items = local.getArtistsSlice(limit = pageSize, offset = offset)
        val total = local.getTotal()

        val next = if (offset + pageSize >= total) null else offset + pageSize
        val prev = if (offset == 0) null else (offset - pageSize).coerceAtLeast(0)

        LoadResult.Page(
            data = items,
            prevKey = prev,
            nextKey = next
        )
    } catch (t: Throwable) {
        LoadResult.Error(t)
    }

}