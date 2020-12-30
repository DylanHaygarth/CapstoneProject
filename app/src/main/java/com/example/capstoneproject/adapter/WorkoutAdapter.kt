package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.model.Exercise
import com.example.capstoneproject.model.FoodItem
import com.example.capstoneproject.model.Workout
import kotlinx.android.synthetic.main.item_exercise.view.*
import kotlinx.android.synthetic.main.item_exercise.view.tvName
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.item_workout.view.*

class WorkoutAdapter(private val workouts: List<Workout>, private val onClick: (Workout) -> Unit) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>(){
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.itemWorkout.setOnClickListener {
                onClick(workouts[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(workout: Workout) {
            itemView.tvWorkoutName.text = workout.name

            val duration = workout.duration
            itemView.tvDuration.text = "$duration min"
            itemView.tvExercises.text = workout.exercises.size.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workouts[position])
    }
}