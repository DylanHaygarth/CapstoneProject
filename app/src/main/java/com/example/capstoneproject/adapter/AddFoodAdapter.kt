package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.model.FoodItem
import kotlinx.android.synthetic.main.item_food.view.*

class AddFoodAdapter(private val foods: List<FoodItem>) : RecyclerView.Adapter<AddFoodAdapter.ViewHolder>(){
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(food: FoodItem) {
            itemView.tvFoodName.text = food.itemName
            itemView.tvCalories.text = food.calories.toString()

            itemView.foodItem.setOnClickListener {
                Toast.makeText(context, context.getString(R.string.add_food_text, food.itemName), Toast.LENGTH_SHORT).show()
            }
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