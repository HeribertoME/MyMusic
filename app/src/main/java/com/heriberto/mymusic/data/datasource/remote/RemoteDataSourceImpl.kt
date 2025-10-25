package com.heriberto.mymusic.data.datasource.remote

import com.heriberto.mymusic.data.datasource.remote.network.api.SpotifyApi
import com.heriberto.mymusic.data.datasource.remote.network.config.ApiCall
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.mapper.toDomain
import com.heriberto.mymusic.domain.model.Artist
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val api: SpotifyApi,
    private val moshi: Moshi
) : RemoteDataSource {

    override suspend fun getFixedArtists(ids: List<String>): NetworkResult<List<Artist>> {
        return ApiCall.execute(moshi) {
            val csv = ids.joinToString(",")
            val envelope = api.getSeveralArtists(csv)
            envelope.artists.map { it.toDomain() }
        }
    }
}