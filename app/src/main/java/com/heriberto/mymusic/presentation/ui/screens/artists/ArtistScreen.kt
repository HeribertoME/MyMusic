package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.heriberto.mymusic.presentation.ui.components.ArtistCard
import com.heriberto.mymusic.presentation.ui.components.EmptyState
import com.heriberto.mymusic.presentation.ui.components.ErrorState
import com.heriberto.mymusic.presentation.ui.components.Header
import com.heriberto.mymusic.presentation.ui.components.LoadingList
import com.heriberto.mymusic.presentation.ui.components.SkeletonItem
import com.heriberto.mymusic.presentation.ui.components.rememberShimmerBrush
import com.heriberto.mymusic.ui.theme.MyMusicTheme
import com.heriberto.mymusic.ui.theme.SkyBlue
import com.heriberto.mymusic.ui.theme.Teal

@Composable
fun ArtistScreen(
    lazyItems: LazyPagingItems<ArtistUi>,
    onArtistClick: (id: String, name: String) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        // sin AppBar
        Header(
            title = "Artistas",
            subtitle = "Mis artistas favoritos",
            brush = Brush.linearGradient(listOf(SkyBlue, Teal))
        )

        // Contenedor con "overlap"
        Surface(
            tonalElevation = 2.dp,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 168.dp)
        ) {
            // Padding interno
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                when (val state = lazyItems.loadState.refresh) {
                    is LoadState.Loading -> LoadingList()
                    is LoadState.Error -> ErrorState(
                        message = state.error.localizedMessage ?: "Error al cargar artistas.",
                        onRetry = { lazyItems.retry() }
                    )

                    is LoadState.NotLoading -> {
                        if (lazyItems.itemCount == 0) {
                            EmptyState(
                                title = "Sin artistas",
                                subtitle = "No hay artistas para mostrar."
                            )
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 28.dp)
                            ) {
                                items(
                                    count = lazyItems.itemCount,
                                    key = { index -> lazyItems[index]?.id ?: index }
                                ) { index ->
                                    val artist = lazyItems[index]
                                    if (artist != null) {
                                        ArtistCard(
                                            name = artist.name,
                                            imageUrl = artist.imageUrl,
                                            onClick = { onArtistClick(artist.id, artist.name) }
                                        )
                                    } else {
                                        SkeletonItem(rememberShimmerBrush())
                                    }
                                }

                                // Footer for append state
                                item {
                                    when (lazyItems.loadState.append) {
                                        is LoadState.Loading -> {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                horizontalArrangement = Arrangement.Center
                                            ) { CircularProgressIndicator(strokeWidth = 2.dp) }
                                        }

                                        is LoadState.Error -> {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                TextButton(onClick = { lazyItems.retry() }) {
                                                    Text("Reintentar")
                                                }
                                            }
                                        }

                                        else -> Unit
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtistScreenPreview() {
    MyMusicTheme {
        Surface { }
    }
}