package com.heriberto.mymusic.presentation.ui.screens.tracks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TracksRoute(
    onBack: () -> Unit,
    viewModel: TracksViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.load() }

    val state by viewModel.uiState.collectAsState()
    TracksScreen(
        albumName = viewModel.albumName,
        albumCover = viewModel.albumCover,
        artistName = viewModel.artistName,
        tracks = state.tracks,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        onRetry = { viewModel.retry() },
        onBack = onBack
    )
}