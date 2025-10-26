package com.heriberto.mymusic.data.mapper

import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.data.datasource.remote.network.responses.ImageDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.SimplifiedAlbumDto
import org.junit.Test

class AlbumMapperTest {
    @Test
    fun `album dto toDomain maps cover and date`() {
        val dto = SimplifiedAlbumDto(
            albumType = "album",
            totalTracks = 10,
            id = "al1",
            name = "An End Has a Start",
            images = listOf(ImageDto("u1", 64, 64), ImageDto("u2", 640, 640)),
            releaseDate = "2007-01-01",
            releaseDatePrecision = "day",
            albumGroup = null
        )

        val domain = dto.toDomain()
        assertThat(domain.coverUrl).isEqualTo("u2")
        assertThat(domain.releaseDate).isEqualTo("2007-01-01")
    }
}