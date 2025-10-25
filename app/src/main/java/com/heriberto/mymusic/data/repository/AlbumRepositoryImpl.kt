package com.heriberto.mymusic.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.heriberto.mymusic.data.datasource.paging.AlbumPagingSource
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.TrackDto
import com.heriberto.mymusic.data.mapper.toDomain
import com.heriberto.mymusic.domain.model.Album
import com.heriberto.mymusic.domain.model.Track
import com.heriberto.mymusic.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource
) : AlbumRepository {

    override fun getArtistAlbumsPaged(
        artistId: String,
        pageSize: Int,
        includeGroups: String,
        market: String?
    ): Flow<PagingData<Album>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 40,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AlbumPagingSource(
                    artistId = artistId,
                    remote = remote,
                    pageSize = 20,
                    includeGroups = includeGroups,
                    market = market
                )
            }
        ).flow
    }

    override fun getAlbumTracks(albumId: String): Flow<List<Track>> = flow {
        val pageSize = 50
        var offset = 0
        val all = mutableListOf<TrackDto>()

        while (true) {
            when (val res = remote.getAlbumTracksPage(albumId, pageSize, offset)) {
                is NetworkResult.Success -> {
                    val body = res.data
                    all += body.items
                    val reachedEnd = offset + body.limit >= body.total
                    if (reachedEnd) break else offset += body.limit
                }

                is NetworkResult.Error -> {
                    throw IOException(res.message ?: "Error al cargar canciones", res.cause)
                }
            }
        }

        emit(all.map { it.toDomain() })
    }
}