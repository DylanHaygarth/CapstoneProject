package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.Exercise
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(){
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(exercise: Exercise) {
            itemView.tvName.text = exercise.name
            itemView.tvSets.text = exercise.sets.toString()
            itemView.tvReps.text = exercise.reps.toString()
            itemView.tvRestTime.text = exercise.restTime.toString()
            itemView.tvWeight.text = exercise.weight.toString()
            itemView.tvNumber.text = (adapterPosition + 1).toString() + "."
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_exercise, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
    }
}