package com.example.fatchum2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailScreenAdapter(private val stringList: List<String>) :
    RecyclerView.Adapter<DetailScreenAdapter.StringViewHolder>() {

    class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.tvIngredientDN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_detail_screen, parent, false)
        return StringViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val currentString = stringList[position]
        holder.textViewItem.text = currentString
    }

    override fun getItemCount() = stringList.size
}

class InstructionDetailAdapter(private val stringList: List<String>) :
    RecyclerView.Adapter<InstructionDetailAdapter.StringViewHolder>() {

    class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem1: TextView = itemView.findViewById(R.id.tvStep)
        val textViewItem: TextView = itemView.findViewById(R.id.tvInstructions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.instruction_detail_screen, parent, false)
        return StringViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val currentString = stringList[position]
        val step = "Step ${position+1}"
        holder.textViewItem.text = currentString
        holder.textViewItem1.text = step
    }

    override fun getItemCount() = stringList.size
}