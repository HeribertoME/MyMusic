package com.heriberto.mymusic.domain.repository

import androidx.paging.PagingData
import com.heriberto.mymusic.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getArtistAlbumsPaged(
        artistId: String,
        pageSize: Int = 20,
        includeGroups: String = "album,single",
        market: String? = null
    ): Flow<PagingData<Album>>
}