package com.example.leopold_jacquet.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.leopold_jacquet.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movietest")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movietest")
    fun getAllFlow(): Flow<List<Movie>>

    @Query("SELECT * FROM movietest WHERE production_year = :value")
    fun flowDateMovies(value: Int): Flow<List<Movie>>

    @Query("SELECT * FROM movietest WHERE id = :id")
    fun findById(id: Int): Movie

    @Upsert
    fun upsertAll(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)
}