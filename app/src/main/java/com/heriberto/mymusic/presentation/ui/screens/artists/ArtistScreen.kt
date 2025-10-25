package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heriberto.mymusic.presentation.ui.components.ArtistCard
import com.heriberto.mymusic.presentation.ui.components.EmptyState
import com.heriberto.mymusic.presentation.ui.components.ErrorState
import com.heriberto.mymusic.presentation.ui.components.Header
import com.heriberto.mymusic.presentation.ui.components.LoadingList
import com.heriberto.mymusic.ui.theme.MyMusicTheme
import com.heriberto.mymusic.ui.theme.SkyBlue
import com.heriberto.mymusic.ui.theme.Teal

@Composable
fun ArtistScreen(
    artists: List<ArtistUi>,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
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
                when {
                    isLoading -> LoadingList()

                    errorMessage != null -> ErrorState(
                        message = errorMessage,
                        onRetry = onRetry
                    )

                    artists.isEmpty() -> EmptyState(
                        title = "Sin artistas",
                        subtitle = "No hay artistas para mostrar."
                    )

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 28.dp)
                        ) {
                            items(artists.size, key = { artists[it].id }) { index ->
                                val a = artists[index]
                                ArtistCard(
                                    name = a.name,
                                    imageUrl = a.imageUrl,
                                    onClick = { onArtistClick(a.id, a.name) }
                                )
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