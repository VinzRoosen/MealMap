package be.LarsVinz.MealMap.Views.Recipes.SelectRecipe

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
import be.LarsVinz.MealMap.Models.RecipeRepository
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentSelectRecipeBinding
import com.google.android.material.snackbar.Snackbar

class SelectRecipeFragment : Fragment(R.layout.fragment_select_recipe) {
    private lateinit var binding: FragmentSelectRecipeBinding
    private lateinit var adapter: SelectRecipeAdapter
    private lateinit var selectedRecipes: MutableList<Recipe>
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var shoppingListRepository: ShoppingListRepository
    private var case: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectRecipeBinding.inflate(layoutInflater)
        selectedRecipes = mutableListOf()
        recipeRepository = RecipeRepository(requireContext())
        shoppingListRepository = ShoppingListRepository(requireContext())
        case = arguments?.getString("case")

        val recipeList = recipeRepository.loadAll()

        adapter = SelectRecipeAdapter(recipeList, selectedRecipes)
        binding.rvwDelete.adapter = adapter
        binding.rvwDelete.layoutManager = LinearLayoutManager(requireContext())

        binding.btnDoneSelecting.setOnClickListener { onDoneSelectingRecipe() }
        binding.btnCancelSelecting.setOnClickListener { onCancelSelecting() }

        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            val recipeFilter = RecipeFilter();
            adapter.filteredList(recipeFilter.filterRecipes(recipeList, text))
        }
        return binding.root
    }

    private fun onDoneSelectingRecipe() {
        if (selectedRecipes.isEmpty()) {
            Snackbar.make(
                requireView(),
                "No recipes selected yet, click on the cross to cancel",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            when (case) {
                "select_shopping_list" -> saveSelectedRecipesToShoppingList()
                "delete_recipes" -> deleteSelectedRecipesFromRecipeList()
            }
        }
    }

    private fun onCancelSelecting() {
        findNavController().popBackStack()
    }


    private fun saveSelectedRecipesToShoppingList() {
        shoppingListRepository.saveIngredientsFromRecipes(selectedRecipes)
        findNavController().navigate(R.id.action_selectRecipeFragment_to_shoppingListFragment)
    }

    private fun deleteSelectedRecipesFromRecipeList() {
        recipeRepository.deleteAllWithPicture(selectedRecipes)
        selectedRecipes.forEach{ shoppingListRepository.deleteAll(it.ingredients)}
        findNavController().navigate(R.id.action_selectRecipeFragment_to_recipeListFragment)
    }
}