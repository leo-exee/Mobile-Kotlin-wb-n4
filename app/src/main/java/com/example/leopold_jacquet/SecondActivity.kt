package com.example.leopold_jacquet

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    private val CHANNEL_ID = "channel_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Movies")
            .setContentText("Here are the movies")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        requestMovieList { result ->
            runOnUiThread {
                findViewById<RecyclerView>(R.id.recyclerView).apply {
                    adapter = MovieAdapter(result.movies)
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@SecondActivity)
                }
            }
            return@requestMovieList result
        }
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@SecondActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@SecondActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
            notify(1, builder.build())
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

    private fun createNotificationChannel() {
        val name = "Movies"
        val descriptionText = "Here are the movies"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}