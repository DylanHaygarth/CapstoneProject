package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.Constants
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.ScheduleAdapter
import com.example.capstoneproject.adapter.WorkoutAdapter
import com.example.capstoneproject.model.ScheduledWorkout
import com.example.capstoneproject.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.btnFriday
import kotlinx.android.synthetic.main.fragment_schedule.btnMonday
import kotlinx.android.synthetic.main.fragment_schedule.btnSaturday
import kotlinx.android.synthetic.main.fragment_schedule.btnSunday
import kotlinx.android.synthetic.main.fragment_schedule.btnThursday
import kotlinx.android.synthetic.main.fragment_schedule.btnTuesday
import kotlinx.android.synthetic.main.fragment_schedule.btnWednesday
import kotlinx.android.synthetic.main.fragment_workout.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {
    private val scheduleViewModel: ScheduleViewModel by activityViewModels()

    private val dayButtons: Array<Button> by lazy { arrayOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday) }
    private var lastSelectedDay = -1

    private val scheduledWorkouts = arrayListOf<ScheduledWorkout>()
    private val scheduledWorkoutsByDay = arrayListOf<ScheduledWorkout>()
    private val scheduleAdapter = ScheduleAdapter(scheduledWorkoutsByDay)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeWorkouts()
        manageSelectedDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        // managing layout of the recycler view
        rvSchedule.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvSchedule.adapter = scheduleAdapter
        rvSchedule.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // adding item touch helper
        createItemTouchHelper().attachToRecyclerView(rvSchedule)

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        fabAddToSchedule.setOnClickListener {
            findNavController().navigate(R.id.addToScheduleFragment)
        }

        val calendar: Calendar = Calendar.getInstance()

        // ensures day of the week is selected upon init
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> { initButton(btnMonday, 0) }
            Calendar.TUESDAY -> { initButton(btnTuesday, 1) }
            Calendar.WEDNESDAY -> { initButton(btnWednesday, 2) }
            Calendar.THURSDAY -> { initButton(btnThursday, 3) }
            Calendar.FRIDAY -> { initButton(btnFriday, 4) }
            Calendar.SATURDAY -> { initButton(btnSaturday, 5) }
            Calendar.SUNDAY -> { initButton(btnSunday, 6) }
        }
    }

    private fun observeWorkouts() {
        scheduleViewModel.scheduledWorkouts.observe(viewLifecycleOwner, Observer {
            scheduledWorkouts.clear()
            scheduledWorkouts.addAll(it)
            addWorkoutsToDay(getDayByButton(dayButtons[lastSelectedDay]))
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButton(button: Button, selectedDay: Int) {
        button.background = resources.getDrawable(R.drawable.day_button_selected_border)
        lastSelectedDay = selectedDay
    }

    // keeps track of the selected day, updates list of foods/date accordingly
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun manageSelectedDay() {
        for (i in dayButtons.indices) {
            dayButtons[i].setOnClickListener {
                dayButtons[i].background = resources.getDrawable(R.drawable.day_button_selected_border)

                if (i != lastSelectedDay) { dayButtons[lastSelectedDay].background = resources.getDrawable(R.drawable.day_button_border) }
                lastSelectedDay = i

                addWorkoutsToDay(getDayByButton(dayButtons[i]))
            }
        }
    }

    // add foods to list based on selected day
    @SuppressLint("SimpleDateFormat")
    private fun addWorkoutsToDay(day: String) {
        scheduledWorkoutsByDay.clear()

        for (i in scheduledWorkouts.indices) {
            if (scheduledWorkouts[i].day == day) {
                scheduledWorkoutsByDay.add(scheduledWorkouts[i])
            }
        }
        scheduleAdapter.notifyDataSetChanged()
    }

    // get correct day based on button
    private fun getDayByButton(button: Button) : String {
        var day = ""

        when (button) {
            dayButtons[0] -> day = Constants.DAYS[0]
            dayButtons[1] -> day = Constants.DAYS[1]
            dayButtons[2] -> day = Constants.DAYS[2]
            dayButtons[3] -> day = Constants.DAYS[3]
            dayButtons[4] -> day = Constants.DAYS[4]
            dayButtons[5] -> day = Constants.DAYS[5]
            dayButtons[6] -> day = Constants.DAYS[6]
        }

        return day
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
                    scheduleViewModel.deleteWorkout(scheduledWorkoutsByDay[position])
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}