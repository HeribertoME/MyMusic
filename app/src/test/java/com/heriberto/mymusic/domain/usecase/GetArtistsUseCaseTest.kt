package com.heriberto.mymusic.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.domain.model.Artist
import com.heriberto.mymusic.domain.repository.ArtistRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetArtistsUseCaseTest {

    private val repo: ArtistRepository = mockk()

    @Test
    fun `invoke emits artists list from repository`() = runTest {
        val list = listOf(
            Artist("1", "Editors", null),
            Artist("2", "Interpol", null)
        )
        every { repo.getArtists() } returns flowOf(list)

        val useCase = GetArtistsUseCase(repo)

        useCase().test {
            assertThat(awaitItem()).containsExactlyElementsIn(list)
            awaitComplete()
        }
    }
}