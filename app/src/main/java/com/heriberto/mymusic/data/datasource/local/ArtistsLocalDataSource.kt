package com.heriberto.mymusic.data.datasource.local

import com.heriberto.mymusic.domain.model.Artist
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistsLocalDataSource @Inject constructor() : LocalDataSource {

    /*private val artists = listOf(
        Artist("1", "Daft Punk", null),
        Artist("2", "Tame Impala", null),
        Artist("3", "Arctic Monkeys", null),
        Artist("4", "Radiohead", null),
        Artist("5", "The Strokes", null),
        Artist("6", "Foals", null),
        Artist("7", "Phoenix", null),
        Artist("8", "Muse", null),
        Artist("9", "The Killers", null),
        Artist("10", "Interpol", null),
        Artist("11", "Kasabian", null),
        Artist("12", "MGMT", null),
        Artist("13", "Blur", null),
        Artist("14", "Oasis", null),
        Artist("15", "Gorillaz", null),
        Artist("16", "Franz Ferdinand", null),
        Artist("17", "Two Door Cinema Club", null),
        Artist("18", "Yeah Yeah Yeahs", null),
        Artist("19", "The Kooks", null),
        Artist("20", "The Libertines", null),
        Artist("21", "The National", null),
        Artist("22", "Arcade Fire", null),
        Artist("23", "The Cure", null),
        Artist("24", "Depeche Mode", null),
        Artist("25", "Beck", null),
        Artist("26", "The Black Keys", null),
        Artist("27", "The 1975", null),
        Artist("28", "Portugal. The Man", null),
        Artist("29", "The Smashing Pumpkins", null),
        Artist("30", "Queens of the Stone Age", null),
    )*/
    private val all: List<Artist> = List(100) { index ->
        val n = index + 1
        Artist(id = n.toString(), name = "Artist $n", imageUrl = null)
    }

    override suspend fun getArtistsSlice(limit: Int, offset: Int): List<Artist> {
        delay(2500)
        return all.drop(offset).take(limit)
    }

    override suspend fun getTotal(): Int = all.size

}