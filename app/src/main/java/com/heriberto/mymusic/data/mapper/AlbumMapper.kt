package com.heriberto.mymusic.data.mapper

import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.SimplifiedAlbumDto
import com.heriberto.mymusic.domain.model.Album

fun SimplifiedAlbumDto.toDomain() = Album(
    id = id,
    name = name,
    coverUrl = images.maxByOrNull { it.width ?: 0 }?.url,
    releaseDate = releaseDate
)