package com.heriberto.mymusic.domain.model

data class Album(
    val id: String,
    val name: String,
    val coverUrl: String?,
    val releaseDate: String?
)