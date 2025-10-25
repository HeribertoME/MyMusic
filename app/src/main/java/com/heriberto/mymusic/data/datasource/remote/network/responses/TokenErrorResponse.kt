package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class TokenErrorResponse(
    @Json(name = "error") val error: String?,
    @Json(name = "error_description") val description: String?
)