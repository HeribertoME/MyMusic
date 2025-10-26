package com.heriberto.mymusic.presentation.ui.screens.tracks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.heriberto.mymusic.ui.theme.MyMusicTheme
import org.junit.Rule
import org.junit.Test

class TracksScreenTest {

    @get:Rule
    val compose = createComposeRule()

    private fun setContent(
        isLoading: Boolean = false,
        errorMessage: String? = null,
        tracks: List<TrackUi> = emptyList(),
        albumName: String = "Test Album",
        albumCover: String? = "https://example.com/cover.jpg",
        artistName: String = "Test Artist",
        onRetry: () -> Unit = {},
        onBack: () -> Unit = {},
    ) {
        compose.setContent {
            MyMusicTheme {
                TracksScreen(
                    albumName = albumName,
                    albumCover = albumCover,
                    artistName = artistName,
                    tracks = tracks,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onRetry = onRetry,
                    onBack = onBack
                )
            }
        }
    }

    @Test
    fun loading_hidesHeaderAndTracks() {
        setContent(isLoading = true, albumName = "Any Album", artistName = "Any Artist")
        // En loading no deben verse titulo/autos
        compose.onNodeWithText("Any Album").assertDoesNotExist()
        compose.onNodeWithText("Any Artist").assertDoesNotExist()
        // No debe existir el botón "Reintentar"
        compose.onNode(hasText("Reintentar", substring = true, ignoreCase = true))
            .assertDoesNotExist()
    }

    @Test
    fun error_showsRetry_and_isClickable() {
        var retried = false
        setContent(errorMessage = "Falla de red", onRetry = { retried = true })

        // Verificar quue exista el texto "Ups..."
        compose.onAllNodesWithText("Ups...", substring = true).onFirst().assertIsDisplayed()

        // Verificar botón Reintentar
        compose.onNode(hasText("Reintentar", substring = true, ignoreCase = true))
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun header_and_firstTrack_areVisible_whenContent() {
        val tracks = listOf(
            TrackUi("t1", "Song A", "Artist A"),
            TrackUi("t2", "Song B", "Artist A")
        )
        setContent(
            tracks = tracks,
            albumName = "An End Has a Start",
            artistName = "Editors",
            albumCover = "https://example.com/cover.jpg"
        )

        // Portada (AsyncImage usa contentDescription = albumName)
        compose.onNode(hasContentDescription("An End Has a Start")).assertIsDisplayed()

        // Título del álbum en header
        compose.onNodeWithText("An End Has a Start").assertIsDisplayed()
        // Artista en header
        compose.onNodeWithText("Editors").assertIsDisplayed()

        // Primera fila
        compose.onNodeWithText("Song A").assertIsDisplayed()
        compose.onAllNodesWithText("Artist A").onFirst().assertIsDisplayed()
    }

    @Test
    fun list_scrolls_to_lastItem() {
        val bigList = (0 until 60).map { i ->
            TrackUi("t$i", "Song #$i", "Editors")
        }
        setContent(tracks = bigList, albumName = "X", artistName = "Editors")

        // Encuentra LazyColumn por la accion de scroll
        val listNode = compose.onAllNodes(hasScrollToIndexAction()).onFirst()
        listNode.performScrollToIndex(bigList.lastIndex)

        // El ultimo elemento debe existir en la lista pero puede no estar visible
        compose.onNodeWithText("Song #59").assertExists()
    }
}