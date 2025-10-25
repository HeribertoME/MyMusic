package com.heriberto.mymusic.data.datasource.remote.network.responses.albums

import com.heriberto.mymusic.data.datasource.remote.network.responses.ImageDto
import com.squareup.moshi.Json

data class SimplifiedAlbumDto(
    @Json(name = "album_type") val albumType: String?,
    @Json(name = "total_tracks") val totalTracks: Int?,
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "images") val images: List<ImageDto> = emptyList(),
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "release_date_precision") val releaseDatePrecision: String?,
    @Json(name = "album_group") val albumGroup: String?
)
