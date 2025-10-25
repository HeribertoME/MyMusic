package com.heriberto.mymusic.domain.usecase

import androidx.paging.PagingData
import com.heriberto.mymusic.domain.model.Album
import com.heriberto.mymusic.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistAlbumPagedUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    operator fun invoke(
        artistId: String,
        pageSize: Int = 20,
        includeGroups: String = "album,single",
        market: String? = null,
    ): Flow<PagingData<Album>> =
        repository.getArtistAlbumsPaged(artistId, pageSize, includeGroups, market)
}