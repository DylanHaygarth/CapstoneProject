package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.capstoneproject.R
import kotlinx.android.synthetic.main.fragment_food.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class FoodFragment : Fragment(R.layout.fragment_food) {
    private val dayButtons: Array<Button> by lazy { arrayOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday) }

    private var lastSelectedDay = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageSelectedDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun manageSelectedDay() {
        for (i in dayButtons.indices) {
            dayButtons[i].setOnClickListener {
                dayButtons[i].background = resources.getDrawable(R.drawable.day_button_selected_border)

                if (lastSelectedDay != -1 && i != lastSelectedDay) { dayButtons[lastSelectedDay].background = resources.getDrawable(R.drawable.day_button_border) }
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