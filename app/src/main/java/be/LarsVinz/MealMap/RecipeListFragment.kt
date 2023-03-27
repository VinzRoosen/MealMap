package be.LarsVinz.MealMap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

        // TODO:Tijdelijk om naar de createRecipeFragment te gaan
        binding.CreateRecipeBtn.setOnClickListener{
            findNavController().navigate(R.id.action_recipeListFragment_to_createRecipeFragment)
        }

        // TODO:Tijdelijk om naar de recipeDetailFragment te gaan
        binding.recipeDetailBtn.setOnClickListener{
            val steps = listOf<RecipeStep>( RecipeStep("Test", 0), RecipeStep("Test2", 0) )
            val recipe = Recipe("Test titel", steps)

            val bundle = bundleOf("recipe" to recipe)

            findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        }

        return binding.root
    }
}