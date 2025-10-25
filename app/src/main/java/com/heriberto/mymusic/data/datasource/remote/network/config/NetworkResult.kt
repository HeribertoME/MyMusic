package com.heriberto.mymusic.data.datasource.remote.network.config

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
        val cause: Throwable? = null
    ) : NetworkResult<Nothing>()
}