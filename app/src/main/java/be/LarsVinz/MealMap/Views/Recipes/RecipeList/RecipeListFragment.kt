package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

        val recipeRepository = RecipePreferencesRepository(requireActivity())
        val recipeList = recipeRepository.loadAllRecipes()

        binding.txtRecipeListEmtyList.text = "no recipes added yet, click + to create a new recipe"
        if (recipeList.isEmpty()){
            binding.txtRecipeListEmtyList.visibility = View.VISIBLE
        } else{
            binding.txtRecipeListEmtyList.visibility = View.GONE
        }

        val adapter = RecipePreviewAdapter(recipeList)
        binding.recipeListRvw.adapter = adapter
        binding.recipeListRvw.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyItemInserted(recipeList.size - 1)

        binding.btnNewRecipe.setOnClickListener{
            findNavController().navigate(R.id.action_recipeListFragment_to_createRecipeFragment)
        }

        binding.btnRemoveRecipe.setOnClickListener{
            findNavController().navigate(R.id.action_recipeListFragment_to_deleteRecipeFragment)
        }

        return binding.root
    }
}