package com.heriberto.mymusic.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.heriberto.mymusic.data.datasource.paging.AlbumPagingSource
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.domain.model.Album
import com.heriberto.mymusic.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
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
}