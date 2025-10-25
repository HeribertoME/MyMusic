package com.heriberto.mymusic.data.datasource.remote.network.responses.tracks

import com.squareup.moshi.Json

data class AlbumTracksResponse(
    @Json(name = "href") val href: String?,
    @Json(name = "items") val items: List<TrackDto>,
    @Json(name = "limit") val limit: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "offset") val offset: Int,
    @Json(name = "previous") val previous: String?,
    @Json(name = "total") val total: Int
)