package com.example.capstoneproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.FoodItem
import kotlinx.android.synthetic.main.item_food.view.*

class FoodAdapter(private val foods: List<EatenFood>, private val onClick: (EatenFood) -> Unit) : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnInfo.setOnClickListener {
                onClick(foods[adapterPosition])
            }
        }

        fun bind(food: EatenFood) {
            itemView.tvFoodName.text = food.name
            itemView.tvCalories.text = food.calories.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foods[position])
    }
}