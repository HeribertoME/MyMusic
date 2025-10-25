package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class ArtistsEnvelope(
    @Json(name = "artists") val artists: List<ArtistDto>
)
