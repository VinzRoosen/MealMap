package be.LarsVinz.MealMap.Views.Recipes.RecipeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.RecipeFragment
import be.LarsVinz.MealMap.Views.Recipes.RecipeStepAdaptor
import be.LarsVinz.MealMap.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private lateinit var binding: FragmentRecipeDetailBinding

    private lateinit var adapter: RecipeStepAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater)

        val recipe = arguments?.getSerializable("recipe") as Recipe

        binding.recipeNameTxt.text = recipe.recipeName

        binding.editBtn.setOnClickListener{

            val bundle = bundleOf("recipe" to recipe)
            findNavController().navigate(R.id.action_recipeDetailFragment_to_createRecipeFragment, bundle)
        }

        binding.backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_recipeDetailFragment_to_recipeListFragment)
        }

        val recipeFragment = RecipeFragment("Edit this recipe to add steps!", "Edit this recipe to add ingredients!")
        recipeFragment.setRecipeData(recipe)

        childFragmentManager.beginTransaction().apply {
            replace(binding.recipeFragment.id, recipeFragment)
            commit()
        }

        return binding.root
    }
}