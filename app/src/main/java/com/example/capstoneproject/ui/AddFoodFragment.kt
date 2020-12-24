package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.Constants
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.AddFoodAdapter
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.FoodItem
import com.example.capstoneproject.viewmodel.FoodViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_food.*
import kotlinx.android.synthetic.main.fragment_settings_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddFoodFragment : Fragment(R.layout.fragment_add_food) {
    private val foods = arrayListOf<FoodItem>()
    private val addFoodAdapter = AddFoodAdapter(foods, ::onFoodClick)

    private val viewModel: FoodViewModel by activityViewModels()

    private var selectedDay = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeFoods()
        getFoods()
    }

    private fun initViews() {
        // managing layout of the recycler view
        rvFoodChoices.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvFoodChoices.adapter = addFoodAdapter
        rvFoodChoices.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        setSpinner()

        btnBackFoodTracker.setOnClickListener {
            findNavController().navigate(R.id.foodFragment)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setSpinner() {
        val days = Constants.DAYS.clone()
        val dayOfTheWeek: String = SimpleDateFormat("EEEE").format(Date())

        // changed current day into 'today' in spinner item list
        for (i in days.indices) {
            if (days[i] == dayOfTheWeek) {
                days[i] = getString(R.string.today_text)
            }
        }

        // set up spinner item
        val adapterOptions: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        adapterOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = adapterOptions

        // sets spinner selection to today
        spinnerDay.setSelection(days.indexOf(getString(R.string.today_text)))

        spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selection = parent?.getItemAtPosition(position).toString()

                selectedDay = if (selection == getString(R.string.today_text)) {
                    dayOfTheWeek
                } else {
                    selection
                }
            }
        }
    }

    // adds foods to food tracker
    @SuppressLint("SimpleDateFormat")
    private fun onFoodClick(food: FoodItem) {
        Toast.makeText(requireContext(), getString(R.string.add_food_text, food.itemName), Toast.LENGTH_SHORT).show()

        val newFood = EatenFood(food.itemName, food.calories, food.fats, food.carbohydrates, food.proteins, selectedDay)
        viewModel.insertFood(newFood)
    }

    private fun observeFoods() {
        viewModel.searchedFoods.observe(viewLifecycleOwner, Observer {
            foods.clear()
            foods.addAll(it)
            addFoodAdapter.notifyDataSetChanged()
        })
    }

    // gets food based on searched query
    private fun getFoods() {
        var searchText = ""

        svFood.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                // converts to lowercase to ensure capital letters do not matter
                searchText = query.toLowerCase(Locale.ROOT)

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean { return true }
        })

        btnFood.setOnClickListener {
            if (searchText.isNotEmpty()) {
                viewModel.getSearchedFoods(searchText, Constants.API_KEY, Constants.API_HOST)
            }
        }
    }
}