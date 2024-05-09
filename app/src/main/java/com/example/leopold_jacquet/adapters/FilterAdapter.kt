package com.example.leopold_jacquet.adapters

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.leopold_jacquet.FilterFragment
import com.example.leopold_jacquet.R

class FilterAdapter(private val filters: List<String>): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filter: Button = itemView.findViewById(R.id.filterButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.filter_button, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.filter.text = filters[position]
        holder.filter.setOnClickListener {
            val filterFragment = FilterFragment.newInstance(filters[position])
            findNavController(holder.itemView).navigate(R.id.action_FirstFragment_to_FilterFragment, filterFragment.arguments)
        }
    }
}