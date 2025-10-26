package com.heriberto.mymusic.data.mapper

import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.ImageDto
import org.junit.Test

class ArtistMapperTest {

    @Test
    fun `toDomain picks largest image`() {
        val dto = ArtistDto(
            externalUrls = null,
            followers = null,
            href = null,
            id = "a1",
            images = listOf(
                ImageDto(url = "small", width = 64, height = 64),
                ImageDto(url = "large", width = 640, height = 640),
                ImageDto(url = "medium", width = 300, height = 300)
            ),
            name = "Editors",
            popularity = null,
            type = null,
            uri = null
        )

        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo("a1")
        assertThat(domain.name).isEqualTo("Editors")
        assertThat(domain.imageUrl).isEqualTo("large")
    }

    @Test
    fun `toDomain null when no contain images`() {
        val dto = ArtistDto(
            externalUrls = null,
            followers = null,
            href = null,
            id = "a1",
            images = emptyList(),
            name = "X",
            popularity = null,
            type = null,
            uri = null
        )

        val domain = dto.toDomain()
        assertThat(domain.imageUrl).isNull()
    }
}