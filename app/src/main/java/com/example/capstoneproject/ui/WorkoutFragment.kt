package com.example.capstoneproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import kotlinx.android.synthetic.main.fragment_workout.*

class WorkoutFragment : Fragment(R.layout.fragment_workout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        btnBackWorkouts.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        fabAddWorkout.setOnClickListener {
            findNavController().navigate(R.id.addWorkoutFragment)
        }
    }
}