package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class ImageDto(
    @Json(name = "url") val url: String,
    @Json(name = "height") val height: Int?,
    @Json(name = "width") val width: Int?
)