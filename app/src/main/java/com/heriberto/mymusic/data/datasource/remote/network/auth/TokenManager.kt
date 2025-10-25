package com.heriberto.mymusic.data.datasource.remote.network.auth

import com.heriberto.mymusic.data.datasource.remote.network.responses.TokenErrorResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val authService: AuthService,
    private val credentials: CredentialsProvider,
    private val store: TokenStore,
    private val moshi: Moshi
) {
    private val mutex = Mutex()

    suspend fun getValidAccessToken(): String {
        store.read()?.let { if (!store.isExpired()) return it }
        return mutex.withLock {
            store.read()?.let { if (!store.isExpired()) return it }
            val resp = runCatching {
                authService.tokenClientCredentials(credentials.basicAuthHeader())
            }.getOrElse { thr ->
                val msg = (thr as? HttpException)?.let { e ->
                    val m = e.response()?.errorBody()?.string().orEmpty()
                    runCatching {
                        moshi.adapter(TokenErrorResponse::class.java).fromJson(m)
                    }.getOrNull()?.let { te ->
                        "${te.error ?: "token_error"}: ${te.description ?: "unknown"}"
                    } ?: "token_error: ${e.message()}"
                } ?: (thr.message ?: "token_error")
                throw IllegalStateException("No se pudo obtener token ($msg)", thr)
            }
            store.write(resp)
            resp.accessToken
        }
    }

    suspend fun forceNewToken(): String = mutex.withLock {
        val resp = authService.tokenClientCredentials(credentials.basicAuthHeader())
        store.write(resp)
        resp.accessToken
    }
}