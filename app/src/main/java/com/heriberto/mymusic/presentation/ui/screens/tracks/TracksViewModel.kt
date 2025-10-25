package com.heriberto.mymusic.presentation.ui.screens.tracks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heriberto.mymusic.domain.usecase.GetAlbumTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val getAlbumTracks: GetAlbumTracksUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val albumId: String = savedStateHandle.get<String>("albumId").orEmpty()
    val albumName: String = savedStateHandle.get<String>("albumName").orEmpty()
    val albumCover: String = savedStateHandle.get<String>("cover").orEmpty()
    val artistName: String = savedStateHandle.get<String>("artist").orEmpty()

    private val _uiState = MutableStateFlow(TracksUiState())
    val uiState: StateFlow<TracksUiState> = _uiState.asStateFlow()

    fun load(force: Boolean = false) {
        val current = _uiState.value
        if (!force && (current.isLoading || current.tracks.isNotEmpty())) return

        viewModelScope.launch {
            getAlbumTracks(albumId)
                .onStart { _uiState.update { it.copy(isLoading = true, errorMessage = null) } }
                .catch { e ->
                    _uiState.value = TracksUiState(
                        tracks = emptyList(),
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar canciones"
                    )
                }
                .collect { list ->
                    _uiState.value = TracksUiState(
                        tracks = list.map { TrackUi(it.id, it.name, it.artistName) },
                        isLoading = false
                    )
                }
        }
    }

    fun retry() = load(force = true)


}