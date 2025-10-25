package com.heriberto.mymusic.di

import com.heriberto.mymusic.data.datasource.local.ArtistsLocalDataSource
import com.heriberto.mymusic.data.datasource.local.LocalDataSource
import com.heriberto.mymusic.data.repository.ArtistRepositoryImpl
import com.heriberto.mymusic.domain.repository.ArtistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(impl: ArtistsLocalDataSource): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindArtistsRepository(impl: ArtistRepositoryImpl): ArtistRepository

}