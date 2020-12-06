package com.example.capstoneproject.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.Constants
import com.example.capstoneproject.R
import com.example.capstoneproject.model.Profile
import com.example.capstoneproject.viewmodel.FitnessViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel: FitnessViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnProfileSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsProfileFragment)
        }

        observeProfile()
    }

    // observes profile data in view model
    private fun observeProfile() {
        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            if (profile == null) {
                initProfile()
            }
            profile?.let {
                setProfile(it)
            }
        })
    }

    private fun setProfile(profile: Profile) {
        tvAge.text = profile.age.toString()
        tvHeight.text = profile.height.toString()
        tvWeight.text = profile.weight.toString()
    }

    // initializes standard settings for profile information
    private fun initProfile() {
        viewModel.insertProfile(Profile(Constants.GENDERS[0], 0, 0, 0, Constants.ACTIVITYOPTIONS[0], Constants.GOALOPTIONS[0], 0, 0))
    }
}