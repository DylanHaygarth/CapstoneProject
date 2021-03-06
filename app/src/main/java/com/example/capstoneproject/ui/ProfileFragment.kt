package com.example.capstoneproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.Constants
import com.example.capstoneproject.api.FoodApi
import com.example.capstoneproject.api.FoodApiService
import com.example.capstoneproject.R
import com.example.capstoneproject.model.Profile
import com.example.capstoneproject.viewmodel.FitnessViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlin.math.roundToInt
import android.util.Log

const val caloriesInKg = 7700
const val daysInMonth = 30

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel: FitnessViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleButtons()
        observeProfile()
    }

    private fun handleButtons() {
        btnProfileSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsProfileFragment)
        }

        btnFoodTracker.setOnClickListener {
            findNavController().navigate(R.id.foodFragment)
        }

        btnSchedule.setOnClickListener {
            findNavController().navigate(R.id.scheduleFragment)
        }

        btnCreateWorkout.setOnClickListener {
            findNavController().navigate(R.id.workoutFragment)
        }
    }

    // observes profile data in view model
    private fun observeProfile() {
        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            if (profile == null) { initProfile() }
            profile?.let {
                setProfile(it)
            }
        })
    }

    private fun setProfile(profile: Profile) {
        val bmi = calculateBMI(profile)
        val bmr = calculateBMR(profile)
        val caloriesMaintenance = calculateCaloriesMaintenance(profile, bmr)
        val caloriesGoal = calculateCaloriesGoal(profile, caloriesMaintenance)
        viewModel.setGoalCalories(caloriesGoal)

        tvAge.text = profile.age.toString()
        tvHeight.text = profile.height.toString()
        tvWeight.text = profile.weight.toString()
        tvBMI.text = bmi.toString()
        tvGoal.text = getString(R.string.fitness_goal, profile.goalAction, profile.goalWeight, profile.goalTime)
        tvBmr.text = bmr.toString()
        tvMaintenance.text = caloriesMaintenance.toString()
        tvGoalCalories.text = caloriesGoal.toString()
    }

    // initializes standard settings for profile information
    private fun initProfile() {
        viewModel.insertProfile(Profile(Constants.GENDERS[0], 0, 0, 0, Constants.ACTIVITYOPTIONS[0], Constants.GOALOPTIONS[0], 0, 0))
    }

    // calculates bmi
    private fun calculateBMI(profile: Profile) : Int {
        return if (profile.weight != 0 && profile.height != 0) {
            val height = profile.height * 0.01
            (profile.weight / (height * height)).roundToInt()
        } else {
            0
        }
    }

    // calculates basic metabolic rate
    private fun calculateBMR(profile: Profile) : Int {
        var bmr = 0

        if (profile.weight != 0 && profile.height != 0 && profile.age != 0) {
            bmr = if (profile.gender == Constants.GENDERS[0]) {
                (10 * profile.weight + 6.25 * profile.height - 5 * profile.age + 5).toInt()
            } else {
                (10 * profile.weight + 6.25 * profile.height - 5 * profile.age - 161).toInt()
            }
        }

        return bmr
    }

    // calculates the calories needed to maintain current weight
    private fun calculateCaloriesMaintenance(profile: Profile, bmr: Int) : Int {
        var calories = 0

        when (profile.activityLevel) {
            Constants.ACTIVITYOPTIONS[0] -> calories = (bmr * 1.2).toInt()
            Constants.ACTIVITYOPTIONS[1] -> calories = (bmr * 1.375).toInt()
            Constants.ACTIVITYOPTIONS[2] -> calories = (bmr * 1.465).toInt()
            Constants.ACTIVITYOPTIONS[3] -> calories = (bmr * 1.725).toInt()
            Constants.ACTIVITYOPTIONS[4] -> calories = (bmr * 1.9).toInt()
        }

        return calories
    }

    // calculates calories needed to reach current goal
    private fun calculateCaloriesGoal(profile: Profile, maintenance: Int) : Int {
        return if (profile.goalWeight != 0 && profile.goalTime != 0) {
            if (profile.goalAction == Constants.GOALOPTIONS[0]) {
                (maintenance + (caloriesInKg * profile.goalWeight) / (profile.goalTime * daysInMonth))
            } else {
                (maintenance - (caloriesInKg * profile.goalWeight) / (profile.goalTime * daysInMonth))
            }
        } else {
            0
        }
    }
}