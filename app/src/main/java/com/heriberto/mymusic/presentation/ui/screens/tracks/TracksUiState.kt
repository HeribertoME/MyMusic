package com.heriberto.mymusic.presentation.ui.screens.tracks

data class TracksUiState(
    val tracks: List<TrackUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)