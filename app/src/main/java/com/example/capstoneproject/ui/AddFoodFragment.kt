package com.example.capstoneproject.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.AddFoodAdapter
import com.example.capstoneproject.model.EatenFood
import com.example.capstoneproject.model.FoodItem
import com.example.capstoneproject.viewmodel.FoodViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_food.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddFoodFragment : Fragment(R.layout.fragment_add_food) {
    private val foods = arrayListOf<FoodItem>()
    private val addFoodAdapter = AddFoodAdapter(foods, ::onFoodClick)

    private val viewModel: FoodViewModel by activityViewModels()

    private var searchText = ""

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

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.foodFragment)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun onFoodClick(food: FoodItem) {
        Toast.makeText(requireContext(), getString(R.string.add_food_text, food.itemName), Toast.LENGTH_SHORT).show()

        val dayOfTheWeek: String = SimpleDateFormat("EEEE").format(Date())

        val newFood = EatenFood(food.itemName, food.calories, food.fats, food.carbohydrates, food.proteins, "Thursday")
        viewModel.insertFood(newFood)
    }

    private fun observeFoods() {
        viewModel.searchedFoods.observe(viewLifecycleOwner, Observer {
            foods.clear()
            foods.addAll(it)
            addFoodAdapter.notifyDataSetChanged()
        })
    }

    private fun getFoods() {
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
                viewModel.getSearchedFoods(searchText, "1bee5def6cmsh02dc711dff052c8p1d2ce6jsnd2e173d01f30", "nutritionix-api.p.rapidapi.com")
            }
        }
    }
}