package com.heriberto.mymusic.data.datasource.remote.network.responses.tracks

import com.squareup.moshi.Json

data class TrackDto(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String,
    @Json(name = "artists") val artists: List<SimplifiedArtistDto> = emptyList()
)