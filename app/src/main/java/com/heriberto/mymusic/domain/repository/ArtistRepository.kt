package com.heriberto.mymusic.domain.repository

import androidx.paging.PagingData
import com.heriberto.mymusic.domain.model.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    fun getArtistsPaged(): Flow<PagingData<Artist>>
    fun getArtists(): Flow<List<Artist>>
}