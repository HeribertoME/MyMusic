package com.heriberto.mymusic.presentation.ui.screens.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.heriberto.mymusic.domain.usecase.GetArtistsPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistPaged: GetArtistsPagedUseCase
) : ViewModel() {

    val artistsPaging: Flow<PagingData<ArtistUi>> =
        getArtistPaged()
            .map { paging -> paging.map { it.toUi() } }
            .cachedIn(viewModelScope)

}