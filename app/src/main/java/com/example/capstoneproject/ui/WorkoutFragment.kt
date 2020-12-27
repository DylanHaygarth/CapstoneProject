package com.example.capstoneproject.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.WorkoutAdapter
import com.example.capstoneproject.model.Workout
import com.example.capstoneproject.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_workout.*

class WorkoutFragment : Fragment(R.layout.fragment_workout) {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val workouts = arrayListOf<Workout>()
    private val workoutAdapter = WorkoutAdapter(workouts, ::onClickWorkout)

    private val bottomSheetInfo = WorkoutBottomSheetFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeWorkouts()
    }

    private fun initViews() {
        // managing layout of the recycler view
        rvWorkouts.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvWorkouts.adapter = workoutAdapter
        rvWorkouts.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // adding item touch helper
        createItemTouchHelper().attachToRecyclerView(rvWorkouts)

        btnBackWorkouts.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        fabAddWorkout.setOnClickListener {
            findNavController().navigate(R.id.addWorkoutFragment)
        }
    }

    // observes the workouts added through the 'add workout' page
    private fun observeWorkouts() {
        workoutViewModel.workouts.observe(viewLifecycleOwner, Observer {
            workouts.clear()
            workouts.addAll(it)
            workoutAdapter.notifyDataSetChanged()
        })
    }

    // opens bottom sheet when user clicks on workout
    private fun onClickWorkout (workout: Workout) {
        bottomSheetInfo.show(requireActivity().supportFragmentManager, "bottomSheetExercises")
        workoutViewModel.addClickedExercises(workout.exercises)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                // deletes the swiped food
                if (direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT) {
                    workoutViewModel.deleteWorkout(workouts[position])
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}