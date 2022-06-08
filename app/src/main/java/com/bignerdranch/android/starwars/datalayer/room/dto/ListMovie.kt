package com.bignerdranch.android.starwars.datalayer.room.dto
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteFilms")
data class ListMovie(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double
)