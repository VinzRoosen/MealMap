package be.LarsVinz.MealMap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

        //New Recept
        val recipeList = mutableListOf<Recipe>()
        val adapter = RecipePreviewAdapter(recipeList)
        binding.RecycleViewMain.adapter = adapter
        binding.RecycleViewMain.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyItemInserted(recipeList.size - 1)

        return binding.root
    }
}
