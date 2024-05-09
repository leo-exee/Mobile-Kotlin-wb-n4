package com.example.leopold_jacquet.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.leopold_jacquet.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie")
    fun getAllFlow(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE production_year = :value")
    fun flowDateMovies(value: Int): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun findById(id: Int): Movie

    @Upsert
    fun upsertAll(vararg movies: Movie)
}