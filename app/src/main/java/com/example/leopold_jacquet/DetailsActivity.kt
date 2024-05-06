package com.example.leopold_jacquet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.leopold_jacquet.entities.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity: AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        db = AppDatabase.getInstance(this)
        val navController = findNavController(R.id.nav_graph_details_router)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onStart() {
        super.onStart()
        val movieId = intent.getIntExtra("movieId", 0)
        requestMovieById(movieId) { movie ->
            displayMovie(movie)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_graph_details_router)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun displayMovie(movie: Movie) {
        val instance = DetailsFragment.newInstance(movie.title, movie.production_year.toString(), movie.poster ?: "")
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_details, instance).commit()
    }

    private fun requestMovieById(movieId: Int, callback: (Movie) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = db.movieDao().findById(movieId)
            callback(movie)
        }
    }
}