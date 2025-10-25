package com.heriberto.mymusic.presentation.ui.screens.albums

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun AlbumsRoute(
    onBack: () -> Unit,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val lazyItems = viewModel.albumsPaging.collectAsLazyPagingItems()
    AlbumsScreen(
        artistName = viewModel.artistName,
        albums = lazyItems,
        onBack = onBack,
        onAlbumClick = { _, _ -> /* todo navigate to tracks */ }
    )
}