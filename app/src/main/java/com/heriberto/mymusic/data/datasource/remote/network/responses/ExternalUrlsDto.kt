package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class ExternalUrlsDto(
    @Json(name = "spotify") val spotify: String?
)
