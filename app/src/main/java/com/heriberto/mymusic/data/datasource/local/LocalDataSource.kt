package com.heriberto.mymusic.data.datasource.local

import com.heriberto.mymusic.domain.model.Artist

interface LocalDataSource {
    suspend fun getArtistsSlice(limit: Int, offset: Int): List<Artist>
    suspend fun getTotal(): Int
}