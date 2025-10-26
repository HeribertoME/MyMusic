package com.heriberto.mymusic.presentation.ui.screens.artists

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.usecase.GetArtistsUseCase
import com.heriberto.mymusic.testutil.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class ArtistViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun flowSuccess(): Flow<List<Artist>> = flow {
        emit(listOf(Artist("1", "Editors", null)))
    }

    private fun flowError(): Flow<List<Artist>> = flow {
        throw IOException("Error")
    }

    @Test
    fun `load success updates state with data`() = runTest {
        val useCase = mockk<GetArtistsUseCase>()
        every { useCase.invoke() } returns flowSuccess()

        val viewModel = ArtistViewModel(getArtist = useCase)

        viewModel.uiState.test {
            // initial state
            assertThat(awaitItem()).isEqualTo(ArtistUiState())

            viewModel.load()
            // loading
            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            // success
            val success = awaitItem()
            assertThat(success.isLoading).isFalse()
            assertThat(success.artists).hasSize(1)
            assertThat(success.errorMessage).isNull()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `load error updates state with error`() = runTest {
        val useCase = mockk<GetArtistsUseCase>()
        every { useCase.invoke() } returns flowError()

        val viewModel = ArtistViewModel(getArtist = useCase)

        viewModel.uiState.test {
            // initial state
            assertThat(awaitItem()).isEqualTo(ArtistUiState())

            viewModel.load(force = true)
            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            // error
            val error = awaitItem()
            assertThat(error.isLoading).isFalse()
            assertThat(error.artists).isEmpty()
            assertThat(error.errorMessage).isNotEmpty()
            cancelAndConsumeRemainingEvents()
        }
    }

}