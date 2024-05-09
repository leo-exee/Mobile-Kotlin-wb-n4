package com.example.leopold_jacquet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

private const val TITLE = "title"
private const val PRODUCTION_YEAR = "productionYear"
private const val POSTER = "poster"
private const val IMDB_ID = "imdb_id"
private const val TMDB_ID = "tmdb_id"
class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? DetailsActivity)?.apply {
            supportActionBar?.title = arguments?.getString("title")
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(TITLE)
        val productionYear = arguments?.getString(PRODUCTION_YEAR)
        val poster = arguments?.getString(POSTER)
        val imdbId = arguments?.getString(IMDB_ID)
        val tmdbId = arguments?.getString(TMDB_ID)

        (activity as? DetailsActivity)?.supportActionBar?.title = title
        view.findViewById<TextView>(R.id.title).text = title
        view.findViewById<TextView>(R.id.production_year).text = productionYear

        poster?.let {
            if (it.isNotEmpty()) {
                Picasso.get().load(it).into(view.findViewById<ImageView>(R.id.poster))
            }
        }

        view.findViewById<Button>(R.id.imdb).apply {
            visibility = if (!imdbId.isNullOrEmpty()) View.VISIBLE else View.GONE
            setOnClickListener {
                imdbId?.let {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/$it")))
                }
            }
        }

        view.findViewById<Button>(R.id.tmdb).apply {
            visibility = if (!tmdbId.isNullOrEmpty()) View.VISIBLE else View.GONE
            setOnClickListener {
                tmdbId?.let {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/movie/$it")))
                }
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            productionYear: String,
            poster: String,
            imdbId: String,
            tmdbId: String
        ) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(PRODUCTION_YEAR, productionYear)
                putString(POSTER, poster)
                putString(IMDB_ID, imdbId)
                putString(TMDB_ID, tmdbId)
            }
        }
    }
}
