package com.example.leopold_jacquet

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.leopold_jacquet.adapters.MovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val VALUE_PARAM = "value"

class FilterFragment : Fragment() {

    private val channelId = "channel_id"
    private var filter: String? = null
    private var value: String? = null

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        arguments?.let {
            value = it.getString(VALUE_PARAM)
        }
        (activity as MainActivity).supportActionBar?.title = "${resources.getString(R.string.production_year)}: $value"
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, ListActivity::class.java),
            PendingIntent.FLAG_MUTABLE
        )
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(resources.getString(R.string.movies))
            .setContentText("${resources.getString(R.string.movies_from_year)} $value")
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "${resources.getString(R.string.movies_from_year)} $value. ${resources.getString(R.string.movies_from_year2)}"
            ))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.ic_launcher_foreground, resources.getString(R.string.see_all), action)
        filterMovies{
            return@filterMovies
        }
        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
            notify(1, builder.build())
        }
    }

    private fun filterMovies(callback: () -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        val movies = db.movieDao().flowDateMovies( value?.toInt() ?: 0)
        movies.collect() {
            CoroutineScope(Dispatchers.Main).launch {
                view?.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
                    adapter = MovieAdapter(it)
                    layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                }
            }
        }
        callback()
    }

    private fun createNotificationChannel() {
        val name = resources.getString(R.string.movies)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {}
        val notificationManager: NotificationManager =
            getSystemService(requireContext(),
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        @JvmStatic
        fun newInstance(value: String) =
            FilterFragment().apply {
                arguments = Bundle().apply {
                    putString(VALUE_PARAM, value)
                }
            }
    }
}