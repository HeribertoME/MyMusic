package com.heriberto.mymusic.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.heriberto.mymusic.presentation.ui.screens.ArtistListScreen
import com.heriberto.mymusic.presentation.ui.screens.ArtistUi
import com.heriberto.mymusic.ui.theme.MyMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyMusicTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtistListScreen(
                        artists = listOf(
                            ArtistUi("1", "Daft Punk", null),
                            ArtistUi("2", "Tame Impala", null),
                            ArtistUi("3", "Arctic Monkeys", null)
                        ),
                        isLoading = false,
                        errorMessage = null,
                        onArtistClick = { id, name -> /* todo navigate to albums */ }
                    )
                }
            }
        }
    }
}