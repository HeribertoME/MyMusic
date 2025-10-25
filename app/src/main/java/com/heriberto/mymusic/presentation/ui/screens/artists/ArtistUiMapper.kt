package com.heriberto.mymusic.presentation.ui.screens.artists

import com.heriberto.mymusic.domain.model.Artist

fun Artist.toUi() = ArtistUi(id, name, imageUrl)