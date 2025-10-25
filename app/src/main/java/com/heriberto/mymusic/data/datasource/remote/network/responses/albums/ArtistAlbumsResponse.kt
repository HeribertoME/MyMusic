package com.heriberto.mymusic.data.datasource.remote.network.responses.albums

import com.squareup.moshi.Json

data class ArtistAlbumsResponse(
    @Json(name = "href") val href: String?,
    @Json(name = "limit") val limit: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "offset") val offset: Int,
    @Json(name = "previous") val previous: String?,
    @Json(name = "total") val total: Int,
    @Json(name = "items") val items: List<SimplifiedAlbumDto>
)