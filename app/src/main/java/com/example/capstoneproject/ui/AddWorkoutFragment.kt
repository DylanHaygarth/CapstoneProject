package com.example.capstoneproject.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.ExerciseAdapter
import com.example.capstoneproject.model.Exercise
import com.example.capstoneproject.model.Workout
import com.example.capstoneproject.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_workout.*

class AddWorkoutFragment : Fragment(R.layout.fragment_add_workout) {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val exercises = arrayListOf<Exercise>()
    private val exerciseAdapter = ExerciseAdapter(exercises)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeSelectedExercises()
        observeWorkoutName()
    }

    private fun initViews() {
        // managing layout of the recycler view
        rvExercises.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvExercises.adapter = exerciseAdapter
        rvExercises.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        btnBackAddWorkout.setOnClickListener {
            findNavController().navigate(R.id.workoutFragment)
        }

        btnFinishTraining.setOnClickListener {
            onAddWorkout()
            findNavController().navigate(R.id.workoutFragment)
        }

        btnAddExercise.setOnClickListener {
            workoutViewModel.setWorkoutName(etName.text.toString())
            findNavController().navigate(R.id.addExerciseFragment)
        }
    }

    private fun observeSelectedExercises() {
        workoutViewModel.selectedExercises.observe(viewLifecycleOwner, Observer {
            exercises.clear()
            exercises.addAll(it)
            exerciseAdapter.notifyDataSetChanged()
        })
    }

    private fun observeWorkoutName() {
        workoutViewModel.workoutName.observe(viewLifecycleOwner, Observer {
            etName.setText(it)
        })
    }

    private fun onAddWorkout() {
        workoutViewModel.insertWorkout(Workout(etName.text.toString(), exercises))
    }
}