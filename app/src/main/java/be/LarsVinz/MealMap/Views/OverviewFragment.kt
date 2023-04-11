package be.LarsVinz.MealMap.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(layoutInflater)

        return binding.root
    }
}