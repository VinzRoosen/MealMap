package be.LarsVinz.MealMap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
=======
>>>>>>> ea6be0ea979ebe5da512e12e74909e9b66ea304b
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)

<<<<<<< HEAD
        val recipeList = mutableListOf<Recipe>()

        //TODO: Tijdelijk
        val steps = listOf<RecipeStep>( RecipeStep("Test", 0), RecipeStep("Test2", 0) )
        recipeList.add(Recipe("test",steps))
        recipeList.add(Recipe("test 2", steps))

        if (recipeList.isEmpty()){
            binding.txtRecipeListEmtyList.text = "no recipes added yet, click 'new' to create a new recipe"
        } else{
            binding.txtRecipeListEmtyList.text = ""
        }

        //TODO: Lars zijn CreateRecipe samenvoegen met recipeList
=======
        //New Recept
        val recipeList = mutableListOf<Recipe>()
>>>>>>> ea6be0ea979ebe5da512e12e74909e9b66ea304b
        val adapter = RecipePreviewAdapter(recipeList)
        binding.RecycleViewMain.adapter = adapter
        binding.RecycleViewMain.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyItemInserted(recipeList.size - 1)
<<<<<<< HEAD


        binding.btnNewRecipe.setOnClickListener{
            findNavController().navigate(R.id.action_recipeListFragment_to_createRecipeFragment)
        }


        // TODO:Tijdelijk om naar de recipeDetailFragment te gaan
        /*
            val recipe = Recipe("Test titel", steps)

            val bundle = bundleOf("recipe" to recipe)

            findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        */
=======
>>>>>>> ea6be0ea979ebe5da512e12e74909e9b66ea304b

        return binding.root
    }
}
