package com.heriberto.mymusic.presentation.ui.screens.albums

import com.heriberto.mymusic.domain.model.Album

fun Album.toUi() = AlbumUi(
    id = id,
    name = name,
    coverUrl = coverUrl,
    subtitle = extractYear(releaseDate)
)

fun extractYear(releaseDate: String?): String? {
    if (releaseDate.isNullOrBlank()) return null
    val year = releaseDate.takeWhile { it.isDigit() }
    return if (year.length == 4) year else null
}