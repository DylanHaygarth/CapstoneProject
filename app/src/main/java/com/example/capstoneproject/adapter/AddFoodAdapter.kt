package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.FoodItem
import kotlinx.android.synthetic.main.item_food.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddFoodAdapter(private val foods: List<FoodItem>, private val onClick: (FoodItem) -> Unit) : RecyclerView.Adapter<AddFoodAdapter.ViewHolder>(){
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.foodItem.setOnClickListener {
                onClick(foods[adapterPosition])
            }
        }

        fun bind(food: FoodItem) {
            itemView.tvFoodName.text = food.itemName
            itemView.tvCalories.text = food.calories.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_food_add, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foods[position])
    }
}