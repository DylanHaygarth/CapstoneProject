package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.capstoneproject.R
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.btnFriday
import kotlinx.android.synthetic.main.fragment_schedule.btnMonday
import kotlinx.android.synthetic.main.fragment_schedule.btnSaturday
import kotlinx.android.synthetic.main.fragment_schedule.btnSunday
import kotlinx.android.synthetic.main.fragment_schedule.btnThursday
import kotlinx.android.synthetic.main.fragment_schedule.btnTuesday
import kotlinx.android.synthetic.main.fragment_schedule.btnWednesday
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {
    private val dayButtons: Array<Button> by lazy { arrayOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday) }
    private var lastSelectedDay = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        manageSelectedDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
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
            }
        }
    }
}