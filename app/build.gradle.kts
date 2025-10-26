import java.util.Properties

// Carga de local.properties
val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use(::load)
}

val spotifyClientId = (localProps.getProperty("SPOTIFY_CLIENT_ID") ?: "").trim()
val spotifyClientSecret = (localProps.getProperty("SPOTIFY_CLIENT_SECRET") ?: "").trim()

// Warning of missing credentials
if (spotifyClientId.isBlank() || spotifyClientSecret.isBlank()) {
    logger.warn(
        """
        ⚠️  Spotify credentials missing.
        Agrega en local.properties (raíz del proyecto):
        SPOTIFY_CLIENT_ID=tu_client_id
        SPOTIFY_CLIENT_SECRET=tu_client_secret

        Se usarán valores placeholder para no fallar el build.
        """.trimIndent()
    )
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.heriberto.mymusic"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.heriberto.mymusic"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SPOTIFY_CLIENT_ID", "\"$spotifyClientId\"")
        buildConfigField("String", "SPOTIFY_CLIENT_SECRET", "\"$spotifyClientSecret\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Icons
    implementation(libs.androidx.compose.material.icons.extended)
    // Coil
    implementation(libs.coil.compose)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    // Okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    // moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Unit test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    // Compose unit tests for components
    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.ui.test.junit4)
    // For network tests
    // MockWebServer + OkHttp
    testImplementation(libs.mockwebserver)
    testImplementation(libs.okhttp)
    // Moshi
    testImplementation(libs.moshi.kotlin)
    // Retrofit
    testImplementation(libs.retrofit)
    testImplementation(libs.retrofit.converter.moshi)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}