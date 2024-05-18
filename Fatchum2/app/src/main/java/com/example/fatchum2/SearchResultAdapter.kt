package com.example.fatchum2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultAdapter(private val searchResults: List<String>, private val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(ingredient: String)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIngredientName: TextView = view.findViewById(R.id.tvIngredientName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = searchResults[position]
        holder.tvIngredientName.text = ingredient
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(ingredient)
        }
    }

    override fun getItemCount() = searchResults.size
}