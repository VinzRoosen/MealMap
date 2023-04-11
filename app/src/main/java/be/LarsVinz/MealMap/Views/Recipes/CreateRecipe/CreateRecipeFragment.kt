package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.RecipeFragment
import be.LarsVinz.MealMap.databinding.FragmentCreateRecipeBinding


class CreateRecipeFragment : Fragment(R.layout.fragment_create_recipe) {

    private lateinit var binding: FragmentCreateRecipeBinding

    private val ingredientList = mutableListOf<Ingredient>()
    private val recipeStepList = mutableListOf<RecipeStep>()

    private val recipeFragment = RecipeFragment("Click + to add a recipe step", "Click here to add an ingredient!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(layoutInflater)

        val recipe = arguments?.getSerializable("recipe") as Recipe?
        recipe?.let {

            ingredientList.addAll(it.ingredients)
            recipeStepList.addAll(it.steps)
            binding.recipeNameTxt.setText(it.name)
        }

        recipeFragment.setRecipeData(ingredientList, recipeStepList)

        childFragmentManager.beginTransaction().apply {
            replace(binding.recipeFragment.id, recipeFragment)
            commit()
        }

        setClickEvents()

        return binding.root
    }

    private fun setClickEvents(){

        binding.addRecipeBtn.setOnClickListener  { openEditStepPopup(null) }
        binding.saveRecipeBtn.setOnClickListener { saveRecipeAndClose() }

        recipeFragment.setOnRecipeStepClicked{
            openEditStepPopup(it)
        }

        recipeFragment.setOnIngredientClicked {
            openEditIngredientPopup()
        }

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

    private fun openEditStepPopup(recipeStep : RecipeStep?){

        EditRecipeStepPopup(requireContext(), recipeStep, recipeStepList).apply {

            setOnDismissListener { recipeFragment.onRecipeStepChanged() }
            show()
        }
    }

    private fun openEditIngredientPopup(){

        EditRecipeIngredientPopup(requireContext(), ingredientList).apply {
            setOnDismissListener { recipeFragment.onIngredientChanged() }
            show()
        }
    }

    fun openSaveConfirmationDialog(){

        AlertDialog.Builder(requireContext()).apply {

            setTitle("Save?")
            setMessage("Do you want to save this recipe?")
            setIcon(android.R.drawable.ic_dialog_alert)

            setPositiveButton("Yes") { dialogInterface, id ->
                saveRecipeAndClose()
            }

            setNegativeButton("No"){ dialogInterface, id ->
                findNavController().popBackStack()
            }

            show()
        }
    }
}























