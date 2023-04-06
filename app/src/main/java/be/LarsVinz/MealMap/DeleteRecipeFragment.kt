package be.LarsVinz.MealMap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.databinding.FragmentDeleteRecipeBinding


class DeleteRecipeFragment : Fragment(R.layout.fragment_delete_recipe) {
    private lateinit var binding: FragmentDeleteRecipeBinding

    private lateinit var adapter: RecipeDeleteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteRecipeBinding.inflate(layoutInflater)
        
        val recipeRepository = RecipeRepository(requireActivity())
        val recipeList = recipeRepository.loadAll()

        adapter = RecipeDeleteAdapter(recipeList) {} // no click event needed
        binding.rvwDelete.adapter = adapter
        binding.rvwDelete.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }
}