package com.example.leopold_jacquet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.leopold_jacquet.entities.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity: AppCompatActivity() {
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "movies.db"
        ).fallbackToDestructiveMigration().build()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        val movieId = intent.getIntExtra("movieId", 0)
        requestMovieById(movieId) { movie ->
            displayMovie(movie)
        }
    }

    private fun displayMovie(movie: Movie) {
        actionBar.apply { title = movie.title }
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_details, DetailsFragment.newInstance(movie.title, movie.production_year.toString(), movie.poster ?: ""))
            .commit()
    }

    private fun requestMovieById(movieId: Int, callback: (Movie) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = db.movieDao().findById(movieId)
            callback(movie)
        }
    }
}