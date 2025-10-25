package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class ApiErrorResponse(
    @Json(name = "error") val error: ApiErrorBody?
) {
    data class ApiErrorBody(
        @Json(name = "status") val status: Int?,
        @Json(name = "message") val message: String?
    )
}