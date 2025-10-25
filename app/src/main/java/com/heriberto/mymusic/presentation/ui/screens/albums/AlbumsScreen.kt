package com.heriberto.mymusic.presentation.ui.screens.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.heriberto.mymusic.presentation.ui.components.EmptyState
import com.heriberto.mymusic.presentation.ui.components.ErrorState
import com.heriberto.mymusic.presentation.ui.components.LoadingList
import com.heriberto.mymusic.presentation.ui.components.SkeletonItem
import com.heriberto.mymusic.presentation.ui.components.rememberShimmerBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    artistName: String,
    albums: LazyPagingItems<AlbumUi>,
    onBack: () -> Unit,
    onAlbumClick: (id: String, name: String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(artistName) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
            }
        )

        when (val state = albums.loadState.refresh) {
            is LoadState.Loading -> LoadingList()
            is LoadState.Error -> ErrorState(
                message = state.error.localizedMessage ?: "Error al cargar álbumes.",
                onRetry = { albums.retry() }
            )

            is LoadState.NotLoading -> {
                if (albums.itemCount == 0) {
                    EmptyState(title = "Sin álbumes", subtitle = "No hay resultados.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp),
                    ) {
                        item {
                            Text(
                                text = "Álbumes",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        items(
                            count = albums.itemCount,
                            key = { index -> albums[index]?.id ?: index },
                        ) { index ->
                            val album = albums[index]
                            if (album != null) {
                                AlbumCard(
                                    name = album.name,
                                    year = album.subtitle,
                                    coverUrl = album.coverUrl,
                                    onClick = { onAlbumClick(album.id, album.name) },
                                    showDivider = index < albums.itemCount - 1
                                )
                            } else {
                                SkeletonItem(rememberShimmerBrush())
                            }
                        }

                        item {
                            when (albums.loadState.append) {
                                is LoadState.Loading -> {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        CircularProgressIndicator(strokeWidth = 2.dp)
                                    }
                                }

                                is LoadState.Error -> {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        TextButton(onClick = { albums.retry() }) { Text("Reintentar") }
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