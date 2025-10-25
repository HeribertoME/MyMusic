package com.heriberto.mymusic.data.datasource.remote.network.responses.tracks

import com.squareup.moshi.Json

data class SimplifiedArtistDto(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String
)