package com.example.leopold_jacquet

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.leopold_jacquet.daos.MovieDAO
import com.example.leopold_jacquet.entities.Movie
import com.example.leopold_jacquet.entities.Movies

@Database(entities = [Movie::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDAO
}