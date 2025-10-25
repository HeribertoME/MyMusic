package com.heriberto.mymusic.presentation.ui.screens.albums

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.heriberto.mymusic.domain.usecase.GetArtistAlbumPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAlbumsPaged: GetArtistAlbumPagedUseCase
) : ViewModel() {

    val artistId: String = savedStateHandle.get<String>("artistId").orEmpty()
    val artistName: String = savedStateHandle.get<String>("artistName").orEmpty()
    val albumsPaging = getAlbumsPaged(artistId)
        .map { pagingData -> pagingData.map { it.toUi() } }
        .cachedIn(viewModelScope)

}