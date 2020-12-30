package com.example.capstoneproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.Constants
import com.example.capstoneproject.R
import com.example.capstoneproject.model.ScheduledWorkout
import com.example.capstoneproject.model.Workout
import com.example.capstoneproject.viewmodel.ScheduleViewModel
import com.example.capstoneproject.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_to_schedule.*
import kotlin.properties.Delegates

class AddToScheduleFragment : Fragment(R.layout.fragment_add_to_schedule) {
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    private val scheduleViewModel: ScheduleViewModel by activityViewModels()

    private lateinit var daySelection: String
    private lateinit var workoutSelection: String
    private var workoutSelectionPos = 0

    private val workoutNames = arrayListOf<String>()
    private val workouts = arrayListOf<Workout>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeWorkouts()
        initViews()
    }

    private fun initViews() {
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.scheduleFragment)
        }

        btnAdd.setOnClickListener {
            if (validateAdd()) {
                onAddWorkout()
                findNavController().navigate(R.id.scheduleFragment)
            }
        }

        setSpinner(spinnerDay, Constants.DAYS.clone())
    }

    // observes currently planned in workouts, adds all workout names to the spinner object
    private fun observeWorkouts() {
        workoutViewModel.workouts.observe(viewLifecycleOwner, Observer {
            workouts.clear()
            workouts.addAll(it)

            for (i in it.indices) {
                workoutNames.add(it[i].name)
            }
            setSpinner(spinnerWorkout, workoutNames.toTypedArray())
        })
    }

    // basic setup for a spinner item
    private fun setSpinner(spinner: Spinner, list: Array<String>) {
        val adapterOptions: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapterOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterOptions

        var selection = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selection = parent?.getItemAtPosition(position).toString()

                // sets value of selected option in the spinner item
                when (spinner) {
                    spinnerDay -> daySelection = selection
                    spinnerWorkout -> {
                        workoutSelection = selection
                        workoutSelectionPos = position
                    }
                }
            }
        }
    }

    // adds a scheduled workout to room database
    private fun onAddWorkout() {
        val startHour = etTimeHour.text.toString().toInt()
        val startMin = etTimeMinute.text.toString().toInt()
        val workout = ScheduledWorkout(workoutSelection, daySelection, startHour, startMin, workouts[workoutSelectionPos].duration)

        scheduleViewModel.insertWorkout(workout)
    }

    // checks if information is filled in properly
    private fun validateAdd() : Boolean {
        return if (etTimeHour.text.isNotEmpty() && etTimeHour.text.toString().toInt() >= 0 && etTimeHour.text.toString().toInt() <= 23 &&
            etTimeMinute.text.isNotEmpty() && etTimeMinute.text.toString().toInt() >= 0 && etTimeMinute.text.toString().toInt() <= 59) {
            true
        } else {
            Toast.makeText(requireContext(), getString(R.string.schedule_time_check), Toast.LENGTH_LONG).show()
            false
        }
    }
}