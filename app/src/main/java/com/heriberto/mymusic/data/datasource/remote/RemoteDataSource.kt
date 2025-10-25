package com.heriberto.mymusic.data.datasource.remote

import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.domain.model.Artist

interface RemoteDataSource {
    suspend fun getFixedArtists(ids: List<String>): NetworkResult<List<Artist>>
}