package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    // todo inject use case
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistUiState())
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = ArtistUiState(isLoading = true)
            delay(5000)
            _uiState.value = ArtistUiState(
                artists = listOf(
                    ArtistUi("1", "Daft Punk", null),
                    ArtistUi("2", "Tame Impala", null),
                    ArtistUi("3", "Arctic Monkeys", null)
                ),
                isLoading = false,
                errorMessage = null
            )
        }
    }

    // when use case is ready:
    // fun load() {
    //   viewModelScope.launch {
    //     runCatching { useCase.searchArtistsPaged(\"a\") ... }.onSuccess { ... }.onFailure { ... }
    //   }
    // }


}