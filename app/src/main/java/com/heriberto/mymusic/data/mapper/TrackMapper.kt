package com.heriberto.mymusic.data.mapper

import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.TrackDto
import com.heriberto.mymusic.domain.model.Track

fun TrackDto.toDomain(): Track = Track(
    id = id ?: "$name-${artists.firstOrNull()?.name.orEmpty()}",
    name = name,
    artistName = artists.firstOrNull()?.name.orEmpty()
)