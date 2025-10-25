package com.heriberto.mymusic.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.heriberto.mymusic.data.datasource.paging.ArtistsRemoteOneShotPagingSource
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.SpotifyArtistIds
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.mapper.toDomain
import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.repository.ArtistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

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

    override fun getArtists(): Flow<List<Artist>> = flow {
        when (val res = remote.getFixedArtists(SpotifyArtistIds.ids)) {
            is NetworkResult.Success -> {
                val domainData = res.data.map { it.toDomain() }
                emit(domainData)
            }
            is NetworkResult.Error -> {
                throw IOException(res.message ?: "Error al cargar artistas", res.cause)
            }
        }
    }
}