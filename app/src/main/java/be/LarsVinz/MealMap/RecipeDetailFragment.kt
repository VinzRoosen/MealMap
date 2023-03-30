package be.LarsVinz.MealMap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        adapter = RecipeStepAdaptor(recipe.steps) {} // no click event needed
        binding.recipeStepRvw.adapter = adapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }
}