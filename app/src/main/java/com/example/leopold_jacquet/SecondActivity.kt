package com.example.leopold_jacquet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.leopold_jacquet.databinding.ActivitySecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class SecondActivity : AppCompatActivity() {
    private var _binding: ActivitySecondBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        WebService.startAction(this)

        findViewById<Button>(R.id.button_return).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.text).text = "Loading..."
        requestMovieList { result ->
            runOnUiThread {
                findViewById<TextView>(R.id.text).text = result
            }
            return@requestMovieList result
        }
    }

    fun requestMovieList(callback: (String) -> String) = CoroutineScope(Dispatchers.IO).launch {
        val client = OkHttpClient()
        var request: Request = Request.Builder()
            .url("https://api.betaseries.com/movies/list")
            .get()
            .addHeader("X-BetaSeries-Key", "62cf9d2eef3d")
            .build()

        val response: Response = client.newCall(request).execute()
        callback(response.body?.string().toString())
    }
}