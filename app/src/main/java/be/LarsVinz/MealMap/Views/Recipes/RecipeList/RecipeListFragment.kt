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
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding
import com.google.android.material.snackbar.Snackbar

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding
    private lateinit var searchArrayList: ArrayList<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

        val recipeRepository = RecipePreferencesRepository(requireActivity())
        val recipeList = recipeRepository.loadAllRecipes()
        searchArrayList = arrayListOf()
        searchArrayList.addAll(recipeList)

        binding.txtRecipeListEmtyList.text = "no recipes added yet, click + to create a new recipe"
        if (recipeList.isEmpty()) {
            binding.txtRecipeListEmtyList.visibility = View.VISIBLE
        } else {
            binding.txtRecipeListEmtyList.visibility = View.GONE
        }

        val adapter = RecipePreviewAdapter(recipeList)
        binding.recipeListRvw.adapter = adapter
        binding.recipeListRvw.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyItemInserted(recipeList.size - 1)

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

        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            val recipeFilter = RecipeFilter();
            adapter.filteredList(recipeFilter.filterRecipes(recipeList, text))
        }
        return binding.root
    }
}