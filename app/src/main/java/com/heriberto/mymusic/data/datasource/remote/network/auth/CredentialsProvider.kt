package com.heriberto.mymusic.data.datasource.remote.network.auth

import android.util.Base64
import com.heriberto.mymusic.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialsProvider @Inject constructor() {
    private val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    private val clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET

    fun basicAuthHeader(): String {
        val raw = "$clientId:$clientSecret"
        val b64 = Base64.encodeToString(raw.toByteArray(), Base64.NO_WRAP)
        return "Basic $b64"
    }

}