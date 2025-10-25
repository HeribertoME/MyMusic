package com.heriberto.mymusic.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.heriberto.mymusic.presentation.ui.screens.albums.AlbumsRoute
import com.heriberto.mymusic.presentation.ui.screens.artists.ArtistRoute
import com.heriberto.mymusic.ui.theme.MyMusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyMusicTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val nav = rememberNavController()

                    NavHost(
                        navController = nav,
                        startDestination = "artists"
                    ) {
                        composable("artists") {
                            ArtistRoute(
                                onArtistClick = { id, name ->
                                    nav.navigate("albums/$id/${Uri.encode(name)}")
                                }
                            )
                        }
                        composable(
                            route = "albums/{artistId}/{artistName}",
                            arguments = listOf(
                                navArgument("artistId") { type = NavType.StringType },
                                navArgument("artistName") { type = NavType.StringType }
                            )
                        ) {
                            AlbumsRoute(
                                onBack = { nav.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}