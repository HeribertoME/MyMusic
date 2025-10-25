package com.heriberto.mymusic.presentation.ui.screens.albums

import com.heriberto.mymusic.domain.model.Album

fun Album.toUi() = AlbumUi(
    id = id,
    name = name,
    coverUrl = coverUrl,
    subtitle = releaseDate
)