package be.LarsVinz.OurApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.LarsVinz.OurApp.databinding.FragmentRecipeListBinding

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

        return binding.root
    }
}