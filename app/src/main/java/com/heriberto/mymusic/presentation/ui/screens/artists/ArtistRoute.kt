package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ArtistRoute(
    onArtistClick: (id: String, name: String) -> Unit,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.load() }

    val state by viewModel.uiState.collectAsState()

    ArtistScreen(
        artists = state.artists,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        onRetry = { viewModel.retry() },
        onArtistClick = onArtistClick
    )

}