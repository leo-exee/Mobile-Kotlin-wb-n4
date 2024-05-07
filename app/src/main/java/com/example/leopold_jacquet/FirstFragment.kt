package com.example.leopold_jacquet

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leopold_jacquet.adapters.FilterAdapter
import com.example.leopold_jacquet.adapters.MovieAdapter
import com.example.leopold_jacquet.databinding.FragmentFirstBinding
import com.example.leopold_jacquet.entities.Movies
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlinx.coroutines.flow.Flow

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(requireContext())
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            startActivity(Intent(context, ListActivity::class.java))
        }
        requestMovieList { result ->
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
        val movies = gson.fromJson(response.body!!.string(), Movies::class.java)
        db.movieDao().upsertAll(*movies.movies.toTypedArray())
        displayMovieFilters(resources.configuration.orientation)
        callback(movies)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        displayMovieFilters(newConfig.orientation)
    }

    private fun displayMovieFilters(orientation: Int) = CoroutineScope(Dispatchers.IO).launch {
        val movies = db.movieDao().getAll()
        val productionYearList = movies
        .filter { it.production_year != 0 }
        .map { it.production_year.toString() }
        .distinct()
        .sorted()
        CoroutineScope(Dispatchers.Main).launch {
            view?.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
                adapter = FilterAdapter(productionYearList)
                layoutManager =
                    androidx.recyclerview.widget.GridLayoutManager(requireContext(),
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}