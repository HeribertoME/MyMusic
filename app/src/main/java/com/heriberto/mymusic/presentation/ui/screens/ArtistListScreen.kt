package com.heriberto.mymusic.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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

/**
 * UI model por this screen
 */
data class ArtistUi(
    val id: String,
    val name: String,
    val imageUrl: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListScreen(
    artists: List<ArtistUi>,
    isLoading: Boolean,
    errorMessage: String?,
    onArtistClick: (id: String, name: String) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        // HERO sin AppBar
        Header(
            title = "Artistas",
            subtitle = "Descubre música nueva",
            brush = Brush.linearGradient(listOf(SkyBlue, Teal))
        )

        // Contenedor con “overlap” sobre el hero
        Surface(
            tonalElevation = 2.dp,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 168.dp)
        ) {
            // Padding interno
            Box(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {
                when {
                    isLoading -> LoadingList()
                    errorMessage != null -> ErrorState(
                        message = errorMessage,
                        onRetry = { /* todo */ }
                    )
                    artists.isEmpty() -> EmptyState(
                        title = "Sin artistas",
                        subtitle = "Aquí aparecerán los resultados."
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
private fun ArtistListScreenPreview() {
    MyMusicTheme {
        ArtistListScreen(
            artists = listOf(
                ArtistUi("1", "Daft Punk", "https://i.scdn.co/image/ab6761610000e5ebc6fe…"),
                ArtistUi("2", "Tame Impala", null),
                ArtistUi("3", "Arctic Monkeys", null),
            ),
            isLoading = false,
            errorMessage = null,
            onArtistClick = { _, _ -> }
        )
    }
}