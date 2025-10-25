package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heriberto.mymusic.domain.usecase.GetArtistsUseCase
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
class ArtistViewModel @Inject constructor(
    private val getArtist: GetArtistsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistUiState())
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    fun load(force: Boolean = false) {
        val current = _uiState.value
        if (!force && (current.isLoading || current.artists.isNotEmpty())) return

        viewModelScope.launch {
            getArtist()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                }
                .catch { e ->
                    _uiState.value = ArtistUiState(
                        artists = emptyList(),
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar artistas"
                    )
                }
                .collect { list ->
                    _uiState.value = ArtistUiState(
                        artists = list.map { it.toUi() },
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }

    fun retry() = load(force = true)

}