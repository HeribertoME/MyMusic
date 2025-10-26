package com.heriberto.mymusic.network

import com.google.common.truth.Truth.assertThat
import com.heriberto.mymusic.data.datasource.remote.network.api.SpotifyApi
import com.heriberto.mymusic.data.datasource.remote.network.config.ApiCall
import com.heriberto.mymusic.data.datasource.remote.network.config.NetworkResult
import com.heriberto.mymusic.testutil.MainDispatcherRule
import com.heriberto.mymusic.testutil.readResource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class SpotifyApiMockWebServerTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private lateinit var server: MockWebServer
    private lateinit var moshi: Moshi
    private lateinit var api: SpotifyApi
    private lateinit var client: OkHttpClient

    /**
     * Interceptor emulates real AuthInterceptor
     */
    private val fakeAuthInterceptor = Interceptor { chain ->
        val token = "fake-toke"
        val newReq: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(newReq)
    }

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }

        moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        client = OkHttpClient.Builder()
            .addInterceptor(fakeAuthInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(SpotifyApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getArtistAlbums parses paging object`() = runTest {
        // response
        val body = readResource("fixtures/albums_page_1.json")
        server.enqueue(
            MockResponse()
                .setBody(body)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        )

        // Call
        val resp = api.getArtistAlbums(
            artistId = "xyz123",
            includeGroups = "album,single",
            limit = 2,
            offset = 0
        )

        // Verify request
        val recorded = server.takeRequest(1, TimeUnit.SECONDS)!!
        assertThat(recorded.path)
            .isEqualTo("/v1/artists/xyz123/albums?include_groups=album%2Csingle&limit=2&offset=0")
        assertThat(recorded.getHeader("Authorization")).isEqualTo("Bearer fake-toke")

        // Verify parse
        assertThat(resp.limit).isEqualTo(2)
        assertThat(resp.offset).isEqualTo(0)
        assertThat(resp.total).isEqualTo(3)
        assertThat(resp.items).hasSize(2)
        assertThat(resp.items[0].id).isEqualTo("al_1")
        assertThat(resp.items[0].images[0].url).isEqualTo("u1")
        assertThat(resp.items[1].name).isEqualTo("Album Two")
    }

    @Test
    fun `ApiCall execute maps 401 spotify error to NetworkResult_Error`() = runTest {
        val errorJson = readResource("fixtures/error_401.json")
        server.enqueue(
            MockResponse()
                .setBody(errorJson)
                .setResponseCode(401)
                .addHeader("Content-Type", "application/json")
        )

        val result = ApiCall.execute(moshi) {
            api.getArtistAlbums("xyz", "album,single", 1, 0)
        }

        // It should be error with message
        assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
        val err = result as NetworkResult.Error
        assertThat(err.message).contains("Invalid access token")
    }

}