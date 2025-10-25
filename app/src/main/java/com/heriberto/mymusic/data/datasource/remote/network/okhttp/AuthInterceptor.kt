package com.heriberto.mymusic.data.datasource.remote.network.okhttp

import com.heriberto.mymusic.data.datasource.remote.network.auth.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.host.contains("accounts.spotify.com")) {
            return chain.proceed(request)
        }
        val token = runCatching { kotlinx.coroutines.runBlocking { tokenManager.getValidAccessToken() } }
            .getOrNull()

        val authed = if (token != null) {
            request.newBuilder().addHeader("Authorization", "Bearer $token").build()
        } else request

        return chain.proceed(authed)
    }
}