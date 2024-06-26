package com.example.leopold_jacquet.adapters

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.leopold_jacquet.DetailsActivity
import com.example.leopold_jacquet.R
import com.example.leopold_jacquet.entities.Movie
import com.squareup.picasso.Picasso

private const val MOVIE_ID = "movieId"
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
        if (movies[position].title == "") {
            holder.title.setText(R.string.no_title)
        } else {
            holder.title.text = movies[position].title
        }
        if (movies[position].production_year == 0) {
            holder.production_year.setText(R.string.no_production_year)
        } else {
            holder.production_year.text = "${movies[position].production_year}"
        }

        Picasso.get().load(movies[position].poster).into(holder.poster)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, movies[position].id)
            startActivity(holder.itemView.context, intent, null)
        }
    }
}