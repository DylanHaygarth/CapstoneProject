package com.example.capstoneproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.model.Exercise
import com.example.capstoneproject.viewmodel.FitnessViewModel
import com.example.capstoneproject.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_exercise.*

class AddExerciseFragment : Fragment(R.layout.fragment_add_exercise) {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        btnBackAddExercise.setOnClickListener {
            findNavController().navigate(R.id.addWorkoutFragment)
        }

        btnAddExercise.setOnClickListener {
            if (validateAddExercise()) {
                onAddExercise()
                findNavController().navigate(R.id.addWorkoutFragment)
            }
        }
    }

    private fun onAddExercise() {
        val name = etName.text.toString()
        val sets = etSets.text.toString().toInt()
        val reps = etReps.text.toString().toInt()
        val restTime = etRestTime.text.toString().toInt()
        val weight = etWeight.text.toString().toInt()

        val exercise = Exercise(name, sets, reps, restTime, weight)

        workoutViewModel.addExerciseToSelectedExercises(exercise)
    }

    private fun validateAddExercise() : Boolean {
        return if (etName.text.isNotEmpty() && etSets.text.isNotEmpty() && etReps.text.isNotEmpty() && etRestTime.text.isNotEmpty() && etWeight.text.isNotEmpty()) {
            true
        } else {
            Toast.makeText(requireContext(), getString(R.string.add_exercise_warning), Toast.LENGTH_LONG).show()
            false
        }
    }
}