package com.example.leopold_jacquet.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leopold_jacquet.R
import com.example.leopold_jacquet.entities.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val production_year: TextView = itemView.findViewById(R.id.production_year)
        val poster: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.movie_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (movies[position].title.isEmpty()) {
            holder.title.text = "Title not available for this movie :("
        } else {
            holder.title.text = movies[position].title
        }
        if (movies[position].production_year == 0) {
            holder.production_year.text = "Production year not available for this movie :("
        } else {
            holder.production_year.text = "Production year: " + movies[position].production_year.toString()
        }

        Picasso.get().load(movies[position].poster).into(holder.poster)
    }
}