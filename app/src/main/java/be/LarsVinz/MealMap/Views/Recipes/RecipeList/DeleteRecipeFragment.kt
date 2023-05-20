package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.databinding.FragmentDeleteRecipeBinding
import be.LarsVinz.MealMap.R

class DeleteRecipeFragment : Fragment(R.layout.fragment_delete_recipe) {
    private lateinit var binding: FragmentDeleteRecipeBinding
    private lateinit var adapter: RecipeDeleteAdapter
    private lateinit var toBeDeletedRecipes: ArrayList<Recipe>
    private lateinit var recipeRepository: RecipePreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteRecipeBinding.inflate(layoutInflater)
        toBeDeletedRecipes = ArrayList();
        recipeRepository = RecipePreferencesRepository(requireActivity())

        val recipeList = recipeRepository.loadAll()

        adapter = RecipeDeleteAdapter(recipeList, toBeDeletedRecipes)
        binding.rvwDelete.adapter = adapter
        binding.rvwDelete.layoutManager = LinearLayoutManager(this.context)

        binding.btnDeleteRecipe.setOnClickListener { onDeleteRecipeBtn() }

        return binding.root
    }

    private fun onDeleteRecipeBtn() {
        for (recipe in toBeDeletedRecipes) {
            recipeRepository.delete(recipe)
        }
        findNavController().navigate(R.id.action_deleteRecipeFragment_to_recipeListFragment)
    }
}