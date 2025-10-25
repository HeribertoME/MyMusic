package com.heriberto.mymusic.di

import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.datasource.remote.RemoteDataSourceImpl
import com.heriberto.mymusic.data.datasource.remote.network.api.SpotifyApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: SpotifyApi,
        moshi: Moshi
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiService, moshi)
    }
}