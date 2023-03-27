package be.LarsVinz.OurApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.OurApp.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private lateinit var binding: FragmentRecipeDetailBinding

    private lateinit var adapter: RecipeStepAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater)

        val recipe = (arguments?.getSerializable("mydata") as Recipe?) ?: null

        if (recipe == null) {
            findNavController().navigate(R.id.action_recipeDetailFragment_to_recipeListFragment)
        }

        adapter = RecipeStepAdaptor(recipe.steps, true)
        binding.recipeStepRvw.adapter = adapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }
}