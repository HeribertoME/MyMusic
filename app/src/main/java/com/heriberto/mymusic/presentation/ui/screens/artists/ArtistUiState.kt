package com.heriberto.mymusic.presentation.ui.screens.artists

data class ArtistUiState(
    val artists: List<ArtistUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)