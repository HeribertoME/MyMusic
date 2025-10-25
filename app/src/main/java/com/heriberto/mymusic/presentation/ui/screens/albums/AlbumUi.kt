package com.heriberto.mymusic.presentation.ui.screens.albums

data class AlbumUi(
    val id: String,
    val name: String,
    val coverUrl: String?,
    val subtitle: String?  // release date
)