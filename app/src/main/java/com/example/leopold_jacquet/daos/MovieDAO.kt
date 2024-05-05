package com.example.leopold_jacquet.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.leopold_jacquet.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movietest")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movietest")
    fun getFlow(): Flow<List<Movie>>

    @Query("SELECT * FROM movietest WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Movie>

    @Query("SELECT * FROM movietest WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Movie

    @Query("SELECT * FROM movietest WHERE id = :id")
    fun findById(id: Int): Movie

    @Insert
    fun insertAll(vararg movies: Movie)

    @Upsert
    fun upsertAll(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)
}