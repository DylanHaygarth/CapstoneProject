package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.viewmodel.FitnessViewModel
import kotlinx.android.synthetic.main.fragment_food.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class FoodFragment : Fragment(R.layout.fragment_food) {
    private val viewModel: FitnessViewModel by activityViewModels()
    private val dayButtons: Array<Button> by lazy { arrayOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday) }

    private var lastSelectedDay = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGoalCalories()
        initUI()
        manageSelectedDay()
        fabClick()
    }

    private fun observeGoalCalories() {
        viewModel.goalCalories.observe(viewLifecycleOwner, Observer {
            tvGoalCalories.text = getString(R.string.goal_food_page, it.toString())
        })
    }

    private fun fabClick() {
        fabAddFood.setOnClickListener {
            findNavController().navigate(R.id.addFoodFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUI() {
        val calendar: Calendar = Calendar.getInstance()

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButton(button: Button, selectedDay: Int) {
        setDate(button)
        button.background = resources.getDrawable(R.drawable.day_button_selected_border)
        lastSelectedDay = selectedDay
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun manageSelectedDay() {
        for (i in dayButtons.indices) {
            dayButtons[i].setOnClickListener {
                dayButtons[i].background = resources.getDrawable(R.drawable.day_button_selected_border)

                if (i != lastSelectedDay) { dayButtons[lastSelectedDay].background = resources.getDrawable(R.drawable.day_button_border) }
                lastSelectedDay = i

                setDate(dayButtons[i])
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate(button: Button) {
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val now: LocalDate = LocalDate.now()
        var dateText = ""

        when (button) {
            dayButtons[0] -> dateText = now.with(DayOfWeek.MONDAY).format(formatter).toString()
            dayButtons[1] -> dateText = now.with(DayOfWeek.TUESDAY).format(formatter).toString()
            dayButtons[2] -> dateText = now.with(DayOfWeek.WEDNESDAY).format(formatter).toString()
            dayButtons[3] -> dateText = now.with(DayOfWeek.THURSDAY).format(formatter).toString()
            dayButtons[4] -> dateText = now.with(DayOfWeek.FRIDAY).format(formatter).toString()
            dayButtons[5] -> dateText = now.with(DayOfWeek.SATURDAY).format(formatter).toString()
            dayButtons[6] -> dateText = now.with(DayOfWeek.SUNDAY).format(formatter).toString()
        }
        tvDate.text = dateText
    }
}