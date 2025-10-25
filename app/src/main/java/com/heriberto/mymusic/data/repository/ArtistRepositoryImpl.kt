package com.heriberto.mymusic.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.heriberto.mymusic.data.datasource.paging.ArtistsRemoteOneShotPagingSource
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.repository.ArtistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource
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
            pagingSourceFactory = {
                ArtistsRemoteOneShotPagingSource(remote = remote, pageSize = pageSize)
            }
        ).flow
        emitAll(pageFlow)
    }
}