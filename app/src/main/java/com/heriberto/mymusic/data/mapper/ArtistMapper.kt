package com.heriberto.mymusic.data.mapper

import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistDto
import com.heriberto.mymusic.domain.model.Artist

fun ArtistDto.toDomain() = Artist(
    id = id,
    name = name,
    imageUrl = images.maxByOrNull { it.width ?: 0 }?.url
)