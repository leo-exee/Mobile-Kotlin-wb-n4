package com.example.leopold_jacquet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.leopold_jacquet.adapters.MovieAdapter
import com.example.leopold_jacquet.entities.Movies
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun onStart() {
        super.onStart()
        requestMovieList { result ->
            runOnUiThread {
                findViewById<RecyclerView>(R.id.recyclerView).apply {
                    adapter = MovieAdapter(result.movies)
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@SecondActivity)
                }
            }
            return@requestMovieList result
        }
    }

    fun requestMovieList(callback: (Movies) -> Movies) = CoroutineScope(Dispatchers.IO).launch {
        val client = OkHttpClient()
        var request: Request = Request.Builder()
            .url("https://api.betaseries.com/movies/list")
            .get()
            .addHeader("X-BetaSeries-Key", "62cf9d2eef3d")
            .build()

        val response: Response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Failed to get movies")
        }
        if (response.body == null) {
            throw Exception("Failed to get movies")
        }

        val gson = Gson()
        var movies = gson.fromJson(response.body!!.string(), Movies::class.java)

        callback(movies)
    }
}