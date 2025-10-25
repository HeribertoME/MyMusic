package com.heriberto.mymusic.data.datasource.remote

import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.ArtistAlbumsResponse

interface RemoteDataSource {
    suspend fun getFixedArtists(ids: List<String>): NetworkResult<List<ArtistDto>>
    suspend fun getArtistAlbumsPage(
        artistId: String,
        limit: Int,
        offset: Int,
        includeGroups: String = "album,single",
        market: String? = null
    ): NetworkResult<ArtistAlbumsResponse>
}