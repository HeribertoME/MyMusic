package com.heriberto.mymusic.data.datasource.remote.network.responses

import com.squareup.moshi.Json

data class ArtistDto(
    @Json(name = "external_urls") val externalUrls: ExternalUrlsDto?,
    @Json(name = "followers") val followers: FollowersDto?,
    @Json(name = "genres") val genres: List<String> = emptyList(),
    @Json(name = "href") val href: String?,
    @Json(name = "id") val id: String,
    @Json(name = "images") val images: List<ImageDto> = emptyList(),
    @Json(name = "name") val name: String,
    @Json(name = "popularity") val popularity: Int?,
    @Json(name = "type") val type: String?,
    @Json(name = "uri") val uri: String?
)
