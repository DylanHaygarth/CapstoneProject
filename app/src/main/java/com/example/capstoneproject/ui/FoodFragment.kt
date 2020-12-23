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
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.viewmodel.FitnessViewModel
import com.example.capstoneproject.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.fragment_food.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.adapter.AddFoodAdapter
import com.example.capstoneproject.adapter.FoodAdapter
import kotlinx.android.synthetic.main.fragment_add_food.*
import java.text.SimpleDateFormat


class FoodFragment : Fragment(R.layout.fragment_food) {
    private val fitnessViewModel: FitnessViewModel by activityViewModels()
    private val foodViewModel: FoodViewModel by activityViewModels()

    private val dayButtons: Array<Button> by lazy { arrayOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday) }

    private val foodList = arrayListOf<EatenFood>()
    private val foodListByDay = arrayListOf<EatenFood>()
    private var lastSelectedDay = -1

    private val foodAdapter = FoodAdapter(foodListByDay)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGoalCalories()
        observeFoods()
        initUI()
        manageSelectedDay()
        fabClick()
    }

    // observes the calorie goal of the user
    private fun observeGoalCalories() {
        fitnessViewModel.goalCalories.observe(viewLifecycleOwner, Observer {
            tvGoalCalories.text = getString(R.string.goal_food_page, it.toString())
        })
    }

    // observes the list of foods added through the 'add food' page
    @SuppressLint("SimpleDateFormat")
    private fun observeFoods() {
        foodViewModel.eatenFoods.observe(viewLifecycleOwner, Observer {
            foodList.clear()
            foodList.addAll(it)
            addFoodsToDay(SimpleDateFormat("EEEE").format(Date()))
        })
    }

    // add foods to list based on selected day
    @SuppressLint("SimpleDateFormat")
    private fun addFoodsToDay(day: String) {
        foodListByDay.clear()

        for (i in foodList.indices) {
            if (foodList[i].day == day) {
                foodListByDay.add(foodList[i])
            }
        }
        foodAdapter.notifyDataSetChanged()
    }

    private fun fabClick() {
        fabAddFood.setOnClickListener {
            findNavController().navigate(R.id.addFoodFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUI() {
        // managing layout of the recycler view
        rvFoodList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvFoodList.adapter = foodAdapter
        rvFoodList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

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
        setDate(button)
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

                addFoodsToDay(getDayByButton(dayButtons[i]))
                setDate(dayButtons[i])
            }
        }
    }

    // sets the correct date
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

    // get correct day based on button
    private fun getDayByButton(button: Button) : String {
        var day = ""

        when (button) {
            dayButtons[0] -> day = "Monday"
            dayButtons[1] -> day = "Tuesday"
            dayButtons[2] -> day = "Wednesday"
            dayButtons[3] -> day = "Thursday"
            dayButtons[4] -> day = "Friday"
            dayButtons[5] -> day = "Saturday"
            dayButtons[6] -> day = "Sunday"
        }

        return day
    }
}