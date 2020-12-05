package com.example.capstoneproject

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings_profile.*

val GENDERS = arrayOf("Male", "Female")
val GOALOPTIONS = arrayOf("Gain", "Lose")
val ACTIVITYOPTIONS = arrayOf("Little/no exercise", "Light exercise 1-3 days/wk", "Moderate exercise 3-5 days/wk", "Hard exercise 5-6 days/wk", "Physical job & hard exercise")

class SettingsProfileFragment : Fragment(R.layout.fragment_settings_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()

        btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_settingsProfileFragment_to_profileFragment)
        }
    }

    private fun setUI () {
        val adapterGender: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, GENDERS)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapterGender

        val adapterGoalOptions: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, GOALOPTIONS)
        adapterGoalOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGoal.adapter = adapterGoalOptions

        val adapterActivityOptions: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ACTIVITYOPTIONS)
        adapterActivityOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerActivityLevel.adapter = adapterActivityOptions
    }
}