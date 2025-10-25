package com.heriberto.mymusic.di

import com.heriberto.mymusic.data.datasource.remote.network.api.SpotifyApi
import com.heriberto.mymusic.data.datasource.remote.network.auth.AuthService
import com.heriberto.mymusic.data.datasource.remote.network.okhttp.AuthInterceptor
import com.heriberto.mymusic.data.datasource.remote.network.okhttp.TokenAuthenticator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named("accountsOkHttp")
    fun provideAccountsOkHttp(
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Accounts (token)
    @Provides
    @Singleton
    @Named("accounts")
    fun provideAccountsRetrofit(
        @Named("accountsOkHttp") client: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideAuthService(@Named("accounts") retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    // OkHttp para Web API
    @Provides
    @Singleton
    fun provideOkHttp(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .authenticator(tokenAuthenticator)
        .build()

    // Retrofit para Web API
    @Provides
    @Singleton
    @Named("webapi")
    fun provideWebApiRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideSpotifyApi(@Named("webapi") retrofit: Retrofit): SpotifyApi =
        retrofit.create(SpotifyApi::class.java)
}