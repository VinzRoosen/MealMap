package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipeFilter
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentSelectRecipeBinding
import com.google.android.material.snackbar.Snackbar

class SelectRecipeFragment : Fragment(R.layout.fragment_select_recipe) {
    private lateinit var binding: FragmentSelectRecipeBinding
    private lateinit var adapter: SelectRecipeAdapter
    private lateinit var selectedRecipes: ArrayList<Recipe>
    private lateinit var recipeRepository: RecipePreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectRecipeBinding.inflate(layoutInflater)
        selectedRecipes = ArrayList();
        recipeRepository = RecipePreferencesRepository(requireActivity())
        val recipeList = recipeRepository.loadAllRecipes()

        adapter = SelectRecipeAdapter(recipeList, selectedRecipes)
        binding.rvwDelete.adapter = adapter
        binding.rvwDelete.layoutManager = LinearLayoutManager(this.context)

        binding.btnDeleteRecipe.setOnClickListener { onDeleteRecipeBtn() }
        binding.btnCancel.setOnClickListener({ findNavController().navigate(R.id.action_deleteRecipeFragment_to_recipeListFragment) })

        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            val recipeFilter = RecipeFilter();
            adapter.filteredList(recipeFilter.filterRecipes(recipeList, text))
        }
        return binding.root
    }

    private fun onDeleteRecipeBtn() { //TODO functie staat hier verkeerd
        if (selectedRecipes.isEmpty()) {
            Snackbar.make(
                requireView(),
                "No recipes selected yet, click on the cross to cancel",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            for (recipe in selectedRecipes) {
                recipeRepository.deleteRecipe(recipe)
            }

            findNavController().navigate(R.id.action_deleteRecipeFragment_to_recipeListFragment)
        }
    }
}