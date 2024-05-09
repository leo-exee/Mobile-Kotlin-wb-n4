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

class ListActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        db = AppDatabase.getInstance(this)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        updateFromDatabase()
    }

    private fun updateFromDatabase() = CoroutineScope(Dispatchers.IO).launch {
        val flow = db.movieDao().getAllFlow()
        flow.collect() {
            CoroutineScope(Dispatchers.Main).launch {
                findViewById<RecyclerView>(R.id.recyclerView).apply {
                    adapter = MovieAdapter(it)
                    layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(this@ListActivity)
                }
            }
        }
    }
}