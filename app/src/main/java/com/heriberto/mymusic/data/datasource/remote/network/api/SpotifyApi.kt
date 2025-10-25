package com.heriberto.mymusic.data.datasource.remote.network.api

import com.heriberto.mymusic.data.datasource.remote.network.responses.ArtistsEnvelope
import com.heriberto.mymusic.data.datasource.remote.network.responses.albums.ArtistAlbumsResponse
import com.heriberto.mymusic.data.datasource.remote.network.responses.tracks.AlbumTracksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {
    @GET("v1/artists")
    suspend fun getSeveralArtists(@Query("ids") ids: String): ArtistsEnvelope

    @GET("v1/artists/{id}/albums")
    suspend fun getArtistAlbums(
        @Path("id") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("market") market: String? = null
    ): ArtistAlbumsResponse

    @GET("v1/albums/{id}/tracks")
    suspend fun getAlbumTracks(
        @Path("id") albumId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): AlbumTracksResponse
}