package com.heriberto.mymusic.di

import com.heriberto.mymusic.data.datasource.remote.RemoteDataSource
import com.heriberto.mymusic.data.repository.AlbumRepositoryImpl
import com.heriberto.mymusic.data.repository.ArtistRepositoryImpl
import com.heriberto.mymusic.domain.repository.AlbumRepository
import com.heriberto.mymusic.domain.repository.ArtistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): ArtistRepository {
        return ArtistRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(
        remoteDataSource: RemoteDataSource
    ): AlbumRepository {
        return AlbumRepositoryImpl(remoteDataSource)
    }
}