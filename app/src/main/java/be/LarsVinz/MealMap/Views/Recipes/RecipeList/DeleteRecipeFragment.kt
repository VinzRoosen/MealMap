package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.Models.RecipeRepository
import be.LarsVinz.MealMap.databinding.FragmentDeleteRecipeBinding
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.RecipeDeleteAdapter

class DeleteRecipeFragment : Fragment(R.layout.fragment_delete_recipe) {
    private lateinit var binding: FragmentDeleteRecipeBinding

    private lateinit var adapter: RecipeDeleteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteRecipeBinding.inflate(layoutInflater)
        
        val recipeRepository = RecipePreferencesRepository(requireActivity())
        val recipeList = recipeRepository.loadAllRecipes()

        adapter = RecipeDeleteAdapter(recipeList) {}
        binding.rvwDelete.adapter = adapter
        binding.rvwDelete.layoutManager = LinearLayoutManager(this.context)

        binding.btnDeleteRecipe.setOnClickListener{onDeleteRecipeBtn()}

        return binding.root
    }

    private fun onDeleteRecipeBtn() {
    }
}