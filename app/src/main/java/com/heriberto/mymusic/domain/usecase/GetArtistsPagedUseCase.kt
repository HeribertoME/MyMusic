package com.heriberto.mymusic.domain.usecase

import androidx.paging.PagingData
import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.repository.ArtistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistsPagedUseCase @Inject constructor(private val repository: ArtistRepository) {
    operator fun invoke(): Flow<PagingData<Artist>> = repository.getArtistsPaged()
}