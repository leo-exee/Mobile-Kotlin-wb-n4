package com.example.leopold_jacquet

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.leopold_jacquet.entities.Movie
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        val productionYear = arguments?.getString("productionYear")
        val poster = arguments?.getString("poster")
        view.findViewById<TextView>(R.id.title).text = title
        view.findViewById<TextView>(R.id.production_year).text = productionYear
        Picasso.get().load(poster).into(view.findViewById<ImageView>(R.id.poster))
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            productionYear: String,
            poster: String
        ) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("productionYear", productionYear)
                    putString("poster", poster)
                }
            }
    }
}