package com.heriberto.mymusic.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class StaticListPagingSource<T: Any>(
    private val source: List<T>,
    private val pageSize: Int
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor)
        return page?.prevKey?.plus(pageSize) ?: page?.nextKey?.minus(pageSize)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = try {
        val offset = params.key ?: 0
        val slice = source.drop(offset).take(pageSize)

        val prevKey = if (offset == 0) null else (offset - pageSize).coerceAtLeast(0)
        val nextKey = if (offset + pageSize >= source.size) null else offset + pageSize

        LoadResult.Page(
            data = slice,
            prevKey = prevKey,
            nextKey = nextKey
        )
    } catch (t: Throwable) {
        LoadResult.Error(t)
    }

}