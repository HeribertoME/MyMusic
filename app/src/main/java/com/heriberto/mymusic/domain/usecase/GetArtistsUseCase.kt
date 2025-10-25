package com.heriberto.mymusic.domain.usecase

import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val repository: ArtistRepository) {

    operator fun invoke(): Flow<List<Artist>> =
        repository.getArtists().flowOn(Dispatchers.IO)
}