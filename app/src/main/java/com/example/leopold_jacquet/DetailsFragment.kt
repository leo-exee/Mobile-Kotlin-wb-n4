package com.example.leopold_jacquet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as DetailsActivity).supportActionBar?.title = arguments?.getString("title")
        (activity as DetailsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        val productionYear = arguments?.getString("productionYear")
        val poster = arguments?.getString("poster")
        (activity as DetailsActivity).supportActionBar?.title = title
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
