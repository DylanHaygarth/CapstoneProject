package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.capstoneproject.R
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.viewmodel.FoodViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_food_bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*

class FoodBottomSheetFragment : BottomSheetDialogFragment() {
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSelectedFood()
    }

    private fun observeSelectedFood() {
        foodViewModel.selectedFood.observe(viewLifecycleOwner, Observer {
            setUI(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setUI(food: EatenFood) {
        tvName.text = food.name
        tvCalories.text = food.calories.toString()
        tvFats.text = food.fats.toString() + 'g'
        tvProtein.text = food.protein.toString() + 'g'
        tvCarbs.text = food.carbohydrates.toString() + 'g'
    }
}