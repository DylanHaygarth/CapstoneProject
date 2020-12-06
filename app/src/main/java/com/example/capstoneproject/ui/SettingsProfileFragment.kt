package com.example.capstoneproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.Constants
import com.example.capstoneproject.R
import com.example.capstoneproject.model.Profile
import com.example.capstoneproject.viewmodel.FitnessViewModel
import kotlinx.android.synthetic.main.fragment_settings_profile.*
import java.util.*

class SettingsProfileFragment : Fragment(R.layout.fragment_settings_profile) {
    private val viewModel: FitnessViewModel by viewModels()

    private lateinit var genderSelection: String
    private lateinit var activitySelection: String
    private lateinit var goalSelection: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinners()
        observeProfile()

        btnConfirm.setOnClickListener {
            onAddProfile()
            findNavController().navigate(R.id.action_settingsProfileFragment_to_profileFragment)
        }
    }

    // observes profile data in view model
    private fun observeProfile() {
        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            profile?.let {
                setUI(it)
            }
        })
    }

    private fun setUI (profile: Profile) {
        // edit texts
        etAge.setText(profile.age.toString())
        etHeight.setText(profile.height.toString())
        etWeight.setText(profile.weight.toString())
        etGoalMonths.setText(profile.goalTime.toString())
        etGoalWeight.setText(profile.goalWeight.toString())

        // spinner items
        spinnerGender.setSelection(Constants.GENDERS.indexOf(profile.gender))
        spinnerActivityLevel.setSelection(Constants.ACTIVITYOPTIONS.indexOf(profile.activityLevel))
        spinnerGoal.setSelection(Constants.GOALOPTIONS.indexOf(profile.goalAction))
    }

    private fun setSpinners() {
        setSpinner(spinnerGender, Constants.GENDERS)
        setSpinner(spinnerActivityLevel, Constants.ACTIVITYOPTIONS)
        setSpinner(spinnerGoal, Constants.GOALOPTIONS)
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
                    spinnerGender -> genderSelection = selection
                    spinnerActivityLevel -> activitySelection = selection
                    spinnerGoal -> goalSelection = selection
                }
            }
        }
    }

    // updates profile information
    private fun onAddProfile() {
        val gender = genderSelection
        val age = etAge.text.toString().toInt()
        val height = etHeight.text.toString().toInt()
        val weight = etWeight.text.toString().toInt()
        val activityLevel = activitySelection
        val goalAction = goalSelection
        val goalWeight = etGoalWeight.text.toString().toInt()
        val goalTime = etGoalMonths.text.toString().toInt()

        viewModel.updateProfile(gender, age, height, weight, activityLevel, goalAction, goalWeight, goalTime)
    }
}