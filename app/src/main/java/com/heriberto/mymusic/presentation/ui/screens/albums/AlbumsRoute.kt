package com.heriberto.mymusic.presentation.ui.screens.albums

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun AlbumsRoute(
    onBack: () -> Unit,
    onAlbumClick: (albumId: String, albumName: String, cover: String?, artistName: String) -> Unit,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val lazyItems = viewModel.albumsPaging.collectAsLazyPagingItems()
    AlbumsScreen(
        artistName = viewModel.artistName,
        albums = lazyItems,
        onBack = onBack,
        onAlbumClick = { id, name, cover ->
            onAlbumClick(id, name, cover, viewModel.artistName)
        }
    )
}