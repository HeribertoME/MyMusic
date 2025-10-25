package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class FollowersDto(
    @Json(name = "href") val href: String?,
    @Json(name = "total") val total: Int
)
