package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale.filter

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding
    private lateinit var searchArrayList: ArrayList<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

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
                    "No recipes added yet, click + to create a new recipe",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.action_recipeListFragment_to_deleteRecipeFragment)
            }
        }

        binding.editTextSearch.doOnTextChanged { text, start, before, count ->
            val filteredList: MutableList<Recipe> = mutableListOf()
            for (item: Recipe in recipeList) {
                if (item.name.lowercase().contains(text.toString().lowercase())) {
                    filteredList.add(item);
                }
            }
            adapter.filteredList(filteredList)
        }



        return binding.root
    }
}