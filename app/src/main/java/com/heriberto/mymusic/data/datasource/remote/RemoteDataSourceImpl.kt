package com.heriberto.mymusic.data.datasource.remote

import com.heriberto.mymusic.data.datasource.remote.network.api.SpotifyApi
import com.heriberto.mymusic.data.datasource.remote.network.config.ApiCall
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistDto
import com.squareup.moshi.Moshi
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: SpotifyApi,
    private val moshi: Moshi
) : RemoteDataSource {

    override suspend fun getFixedArtists(ids: List<String>): NetworkResult<List<ArtistDto>> {
        return ApiCall.execute(moshi) {
            val csv = ids.joinToString(",")
            val envelope = api.getSeveralArtists(csv)
            envelope.artists
        }
    }
}