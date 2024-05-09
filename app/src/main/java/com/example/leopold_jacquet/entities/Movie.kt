package com.example.leopold_jacquet.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "production_year")
    var production_year: Int = 0,

    @ColumnInfo(name = "poster", defaultValue = "")
    var poster: String? = "",

    @ColumnInfo(name = "imdb_id", defaultValue = "")
    var imdb_id: String? = "",

    @ColumnInfo(name = "tmdb_id", defaultValue = "")
    var tmdb_id: String? = ""
)