package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.RecipeFilter
import be.LarsVinz.MealMap.Models.RecipeRepository
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding
import com.google.android.material.snackbar.Snackbar

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

        val recipeRepository = RecipeRepository(requireActivity())
        val recipeList = recipeRepository.loadAll() as MutableList

        binding.txtRecipeListEmtyList.text = "no recipes added yet, click + to create a new recipe"
        binding.txtRecipeListEmtyList.visibility = if (recipeList.isEmpty()) View.VISIBLE else View.GONE

        val adapter = RecipePreviewAdapter(recipeList)
        binding.recipeListRvw.adapter = adapter
        binding.recipeListRvw.layoutManager = LinearLayoutManager(requireContext())

        binding.btnNewRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_recipeListFragment_to_createRecipeFragment)
        }

        binding.btnRemoveRecipe.setOnClickListener {
            if (recipeList.isEmpty()) {
                Snackbar.make(
                    it,
                    "no recipes added yet, click on + to select a recipe",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val bundle = Bundle()
                bundle.putString("case", "delete_recipes")
                findNavController().navigate(R.id.action_recipeListFragment_to_deleteRecipeFragment, bundle)
            }
        }

        binding.btnRemoveRecipe.setOnLongClickListener{
            recipeRepository.deleteAll(recipeList)
            val shoppingListRepository = ShoppingListRepository(requireContext())

            for (recipe in recipeList) shoppingListRepository.deleteAll(recipe.ingredients)

            recipeList.clear()
            adapter.notifyDataSetChanged()
            true
        }

        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            val recipeFilter = RecipeFilter();
            adapter.filteredList(recipeFilter.filterRecipes(recipeList, text))
        }
        return binding.root
    }
}