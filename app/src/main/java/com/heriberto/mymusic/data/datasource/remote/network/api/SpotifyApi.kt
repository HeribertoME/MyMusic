package com.heriberto.mymusic.data.datasource.remote.network.api

import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistsEnvelope
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyApi {
    @GET("v1/artists")
    suspend fun getSeveralArtists(@Query("ids") ids: String): ArtistsEnvelope
}