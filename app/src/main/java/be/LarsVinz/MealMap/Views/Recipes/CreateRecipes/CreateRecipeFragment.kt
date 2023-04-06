package be.LarsVinz.MealMap.Views.Recipes.CreateRecipes

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.IngredientAdapter
import be.LarsVinz.MealMap.Views.Recipes.RecipeStepAdaptor
import be.LarsVinz.MealMap.databinding.FragmentCreateRecipeBinding


class CreateRecipeFragment : Fragment(R.layout.fragment_create_recipe) {

    private lateinit var binding: FragmentCreateRecipeBinding

    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var recipeStepAdapter: RecipeStepAdaptor

    private val ingredientList = mutableListOf<Ingredient>()
    private val recipeStepList = mutableListOf<RecipeStep>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(layoutInflater)

        val recipe = arguments?.getSerializable("recipe") as Recipe?
        recipe?.let { setRecipeData(recipe) }

        setRecipeRecycleView()
        setButtons()

        return binding.root
    }

    private fun setRecipeData(recipe : Recipe){

        binding.recipeNameTxt.setText(recipe.recipeName)
        ingredientList.addAll(recipe.ingredients)
        recipeStepList.addAll(recipe.steps)
    }

    private fun setRecipeRecycleView(){

        ingredientAdapter = IngredientAdapter(ingredientList)
        binding.ingredientRvw.adapter = ingredientAdapter
        binding.ingredientRvw.layoutManager = GridLayoutManager(this.context, 2)

        recipeStepAdapter = RecipeStepAdaptor(recipeStepList, requireContext()) { recipeStep -> // This is the OnItemClick
            openEditStepDialog(recipeStep)
        }

        binding.recipeStepRvw.adapter = recipeStepAdapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)
    }

    private fun setButtons(){

        binding.addRecipeBtn.setOnClickListener  { openEditStepDialog(null) }
        binding.saveRecipeBtn.setOnClickListener { saveRecipeAndClose() }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                openSaveConfirmationDialog()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun saveRecipeAndClose(){

        val recipe = Recipe(binding.recipeNameTxt.text.toString(), recipeStepList, ingredientList)

        val repository = RecipePreferencesRepository(requireActivity())
        repository.saveRecipe(recipe)

        val bundle = bundleOf("recipe" to recipe)
        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeDetailFragment, bundle)
    }

    private fun openEditStepDialog(recipeStep : RecipeStep?){

        EditStepPopup(requireContext(), recipeStep, recipeStepList).apply {

            setOnDismissListener { recipeStepAdapter.notifyDataSetChanged() }
            show()
        }
    }

    fun openSaveConfirmationDialog(){

        AlertDialog.Builder(requireContext()).apply {

            setTitle("Save?")
            setMessage("Do you want to save this recipe?")
            setIcon(android.R.drawable.ic_dialog_alert)

            setPositiveButton("Save") { dialogInterface, id ->
                saveRecipeAndClose()
            }

            setNegativeButton("Cancel"){ dialogInterface, id ->
                findNavController().popBackStack()
            }

            show()
        }
    }
}























