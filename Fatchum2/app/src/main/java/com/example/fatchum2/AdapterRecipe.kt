package com.example.fatchum2

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterRecipe(
    private val listRecipe: MutableList<Recipe>,
    private val listener: OnRecipeClickListener
) : RecyclerView.Adapter<AdapterRecipe.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_display, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = listRecipe[position]
        holder.tvRecipeName.text = recipe.recipe_name
        Picasso.get()
            .load(recipe.image_url)
            .into(holder.ivThumbnail)

        holder.itemView.setOnClickListener {
            listener.onRecipeClick(recipe)
        }
    }

    fun addRecipes(newRecipes: MutableList<Recipe>) {
        val startPosition = listRecipe.size
        listRecipe.addAll(newRecipes)
        notifyItemRangeInserted(startPosition, newRecipes.size)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvRecipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbNail)
    }
}

interface OnRecipeClickListener {
    fun onRecipeClick(recipe: Recipe)
}

