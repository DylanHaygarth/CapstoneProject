package com.example.capstoneproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.ExerciseAdapter
import com.example.capstoneproject.model.Exercise
import com.example.capstoneproject.viewmodel.WorkoutViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_workout.*
import kotlinx.android.synthetic.main.fragment_workout_bottom_sheet.*


class WorkoutBottomSheetFragment : BottomSheetDialogFragment() {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val exercises = arrayListOf<Exercise>()
    private val exerciseAdapter = ExerciseAdapter(exercises)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeClickedExercises()
    }

    private fun initViews() {
        // managing layout of the recycler view
        rvClickedExercises.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvClickedExercises.adapter = exerciseAdapter
        rvClickedExercises.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun observeClickedExercises() {
        workoutViewModel.clickedExercises.observe(viewLifecycleOwner, Observer {
            exercises.clear()
            exercises.addAll(it)
            exerciseAdapter.notifyDataSetChanged()
        })
    }
}