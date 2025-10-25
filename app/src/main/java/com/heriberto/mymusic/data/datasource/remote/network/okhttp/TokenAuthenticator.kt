package com.heriberto.mymusic.data.datasource.remote.network.okhttp

import com.heriberto.mymusic.data.datasource.remote.network.auth.TokenManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null
        val newAccess = runCatching { kotlinx.coroutines.runBlocking { tokenManager.forceNewToken() } }.getOrNull()
            ?: return null
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccess")
            .build()
    }
}