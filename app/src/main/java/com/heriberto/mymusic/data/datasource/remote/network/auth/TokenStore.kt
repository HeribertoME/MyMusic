package com.heriberto.mymusic.data.datasource.remote.network.auth

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor() {
    @Volatile private var access: String? = null
    @Volatile private var expiresAtMs: Long = 0L

    fun read(): String? = access
    fun isExpired(nowMs: Long = System.currentTimeMillis()) = nowMs >= expiresAtMs

    fun write(resp: TokenResponse, nowMs: Long = System.currentTimeMillis()) {
        access = resp.accessToken
        expiresAtMs = nowMs + (resp.expiresIn - 10).coerceAtLeast(0) * 1000
    }

    fun clear() { access = null; expiresAtMs = 0L }

}