package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun ArtistRoute(
    onArtistClick: (id: String, name: String) -> Unit,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    val lazyItems = viewModel.artistsPaging.collectAsLazyPagingItems()
    ArtistScreen(
        lazyItems = lazyItems,
        onArtistClick = onArtistClick
    )
}