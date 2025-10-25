package com.heriberto.mymusic.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.heriberto.mymusic.data.datasource.local.LocalDataSource
import com.heriberto.mymusic.data.datasource.paging.ArtistsPagingSource
import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.repository.ArtistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistRepositoryImpl @Inject constructor(
    private val local: LocalDataSource
) : ArtistRepository {

    override fun getArtistsPaged(): Flow<PagingData<Artist>> = flow {
        val pageSize = 20
        val pageFlow = Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize * 2,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArtistsPagingSource(local, pageSize) }
        ).flow
        emitAll(pageFlow)
    }
}