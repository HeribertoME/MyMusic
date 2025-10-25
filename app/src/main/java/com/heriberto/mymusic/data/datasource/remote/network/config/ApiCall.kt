package com.heriberto.mymusic.data.datasource.remote.network.config

import com.heriberto.mymusic.data.datasource.remote.network.responses.ApiErrorResponse
import com.heriberto.mymusic.data.datasource.remote.network.responses.TokenErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException

object ApiCall {
    suspend fun <T> execute(
        moshi: Moshi,
        block: suspend () -> T
    ): NetworkResult<T> = try {
        NetworkResult.Success(block())
    } catch (e: HttpException) {
        val code = e.code()
        val raw = e.response()?.errorBody()
        val msg = parseSpotifyError(moshi, raw, code)
        NetworkResult.Error(code = code, message = msg, cause = e)
    } catch (e: Exception) {
        NetworkResult.Error(message = e.message ?: "Unknown error", cause = e)
    }

    private fun parseSpotifyError(moshi: Moshi, body: ResponseBody?, code: Int): String {
        if (body == null) return "HTTP $code"
        val json = body.string()

        // 1) Web API error shape: { "error": { "status": 401, "message": "..." } }
        runCatching {
            val adapter = moshi.adapter(ApiErrorResponse::class.java)
            val parsed = adapter.fromJson(json)
            val m = parsed?.error?.message
            if (!m.isNullOrBlank()) return "HTTP $code – $m"
        }

        // 2) Token error shape: { "error": "invalid_client", "error_description": "..." }
        runCatching {
            val adapter = moshi.adapter(TokenErrorResponse::class.java)
            val parsed = adapter.fromJson(json)
            val d = parsed?.description ?: parsed?.error
            if (!d.isNullOrBlank()) return "HTTP $code – $d"
        }

        return "HTTP $code"
    }
}