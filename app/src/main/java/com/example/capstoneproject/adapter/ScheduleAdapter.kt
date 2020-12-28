package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.model.ScheduledWorkout
import com.example.capstoneproject.model.Workout
import kotlinx.android.synthetic.main.item_schedule.view.*
import kotlinx.android.synthetic.main.item_workout.view.*
import android.util.Log

const val minInHour = 60
class ScheduleAdapter(private val workouts: List<ScheduledWorkout>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(workout: ScheduledWorkout) {
            itemView.tvName.text = workout.workoutName

            var endHour = workout.startHour
            var endMin = workout.startMin + workout.duration
            while (endMin >= minInHour) {
                if (endHour == 23) {
                    endHour = 0
                } else {
                    endHour++
                }
                endMin -= minInHour
            }

            var hourStartText: String = workout.startHour.toString()
            var hourEndText: String = endHour.toString()
            var minStartText: String = workout.startMin.toString()
            var minEndText: String = endMin.toString()

            if (hourStartText == "0") {hourStartText = "00"}
            if (hourEndText == "0") {hourEndText = "00"}
            if (minStartText == "0") {minStartText = "00"}
            if (minStartText.toInt() in 1..9) {minStartText = "0$minStartText"}
            if (minEndText == "0") {minEndText = "00"}
            if (minEndText.toInt() in 1..9) {minEndText = "0$minEndText"}

            itemView.tvTime.text = context.getString(R.string.workout_time, hourStartText, minStartText, hourEndText, minEndText)
            itemView.tvWorkoutDuration.text = context.getString(R.string.duration_schedule_text, workout.duration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workouts[position])
    }
}