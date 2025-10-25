package com.heriberto.mymusic.data.datasource.remote

import com.heriberto.mymusic.data.datasource.remote.network.api.SpotifyApi
import com.heriberto.mymusic.data.datasource.remote.network.config.ApiCall
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistDto
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.ArtistAlbumsResponse
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.AlbumTracksResponse
import com.squareup.moshi.Moshi
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: SpotifyApi,
    private val moshi: Moshi
) : RemoteDataSource {

    override suspend fun getFixedArtists(ids: List<String>): NetworkResult<List<ArtistDto>> {
        return ApiCall.execute(moshi) {
            val csv = ids.joinToString(",")
            val envelope = api.getSeveralArtists(csv)
            envelope.artists
        }
    }

    override suspend fun getArtistAlbumsPage(
        artistId: String,
        limit: Int,
        offset: Int,
        includeGroups: String,
        market: String?
    ): NetworkResult<ArtistAlbumsResponse> {
        return ApiCall.execute(moshi) {
            api.getArtistAlbums(
                artistId = artistId,
                includeGroups = includeGroups,
                limit = limit,
                offset = offset,
                market = market
            )
        }
    }

    override suspend fun getAlbumTracksPage(
        albumId: String,
        limit: Int,
        offset: Int
    ): NetworkResult<AlbumTracksResponse> {
        return ApiCall.execute(moshi) {
            api.getAlbumTracks(albumId = albumId, limit = limit, offset = offset)
        }
    }
}