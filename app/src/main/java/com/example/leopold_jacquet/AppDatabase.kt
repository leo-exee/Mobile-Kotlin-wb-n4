package com.example.leopold_jacquet

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leopold_jacquet.daos.MovieDAO
import com.example.leopold_jacquet.entities.Movie
import com.example.leopold_jacquet.entities.Movies

@Database(entities = [Movie::class], version = 7)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDAO

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "movies.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}