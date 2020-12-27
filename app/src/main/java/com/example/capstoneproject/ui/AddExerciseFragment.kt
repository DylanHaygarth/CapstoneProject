package com.example.capstoneproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import kotlinx.android.synthetic.main.fragment_add_exercise.*

class AddExerciseFragment : Fragment(R.layout.fragment_add_exercise) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        btnBackAddExercise.setOnClickListener {
            findNavController().navigate(R.id.addWorkoutFragment)
        }

        btnAddExercise.setOnClickListener {
            findNavController().navigate(R.id.addWorkoutFragment)
        }
    }
}