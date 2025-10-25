package com.heriberto.mymusic.domain.usecase

import com.heriberto.mymusic.domain.model.Track
import com.heriberto.mymusic.domain.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAlbumTracksUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    operator fun invoke(albumId: String): Flow<List<Track>> =
        repository.getAlbumTracks(albumId).flowOn(Dispatchers.IO)
}