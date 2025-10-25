package com.heriberto.mymusic.data.datasource.remote.network.auth

import com.squareup.moshi.Json

data class TokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type")   val tokenType: String,
    @Json(name = "expires_in")   val expiresIn: Long
)