package com.heriberto.mymusic.data.repository

import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.ArtistAlbumsResponse
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.AlbumTracksResponse

open class DummyRemote : RemoteDataSource {
    override suspend fun getFixedArtists(ids: List<String>): NetworkResult<List<ArtistDto>> =
        NetworkResult.Error()

    override suspend fun getArtistAlbumsPage(
        artistId: String,
        limit: Int,
        offset: Int,
        includeGroups: String,
        market: String?
    ): NetworkResult<ArtistAlbumsResponse> =
        NetworkResult.Error()

    override suspend fun getAlbumTracksPage(
        albumId: String,
        limit: Int,
        offset: Int
    ): NetworkResult<AlbumTracksResponse> =
        NetworkResult.Error()
}