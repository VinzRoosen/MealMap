package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.Models.ImageFileRepository
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.RecipeFragment
import be.LarsVinz.MealMap.databinding.FragmentCreateRecipeBinding


class CreateRecipeFragment : Fragment(R.layout.fragment_create_recipe) {

    private lateinit var binding: FragmentCreateRecipeBinding
    private lateinit var pictureActivityResult: ActivityResultLauncher<Void?>

    private val ingredientList = mutableListOf<Ingredient>()
    private val recipeStepList = mutableListOf<RecipeStep>()
    private val recipeTagList = mutableListOf<Tag>()

    private var image : Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(layoutInflater)

        val recipe = arguments?.getSerializable("recipe") as Recipe?
        recipe?.let {

            ingredientList.addAll(it.ingredients)
            recipeStepList.addAll(it.steps)
            recipeTagList.addAll(it.tags)
            binding.recipeNameTxt.setText(it.name)
        }

        val recipeFragment = RecipeFragment(true, recipeStepList, ingredientList)
        recipeFragment.setRecipeData(ingredientList, recipeStepList)

        openFragment(recipeFragment)

        setClickEvents()

        pictureActivityResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
            it?.let { image = it }
        }

        return binding.root
    }

    private fun setClickEvents(){

        binding.addTagsBtn.setOnClickListener { openFragment(EditTagsFragment(recipeTagList)) }
        binding.addPictureBtn.setOnClickListener { makePicture() }
        binding.saveRecipeBtn.setOnClickListener {
            val recipe = saveRecipe()
            recipe?.let { navigateToRecipeDetail(it) }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (childFragmentManager.fragments.last() is RecipeFragment){
                    openSaveConfirmationDialog()
                }
                else childFragmentManager.popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun openFragment(fragment: Fragment){

        childFragmentManager.beginTransaction().apply {
            replace(binding.recipeFragment.id, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun makePicture(){

        pictureActivityResult.launch(null)
    }

    private fun saveRecipe() : Recipe? {

        // get the recipe name
        val recipeName = binding.recipeNameTxt.text.toString()
        if (recipeName.isBlank()){
            Toast.makeText(requireContext(), "The recipe needs a name!", Toast.LENGTH_LONG).show()
            return null
        }

        var imagePath : String? = null

        // save image
        image?.let {

            imagePath = "Image_${recipeName}"

            val imageRepository = ImageFileRepository(requireContext())
            imageRepository.saveImage(it, imagePath!!)
        }

        // create recipe
        val recipe = Recipe(recipeName, recipeStepList, ingredientList, recipeTagList, imagePath)

        // save recipe
        val repository = RecipePreferencesRepository(requireActivity())
        repository.saveRecipe(recipe)

        return recipe
    }

    fun openSaveConfirmationDialog(){

        AlertDialog.Builder(requireContext()).apply {

            setTitle("Save?")
            setMessage("Do you want to save this recipe?")
            setIcon(android.R.drawable.ic_dialog_alert)

            setPositiveButton("Yes") { dialogInterface, id ->
                saveRecipe()
            }

            setNegativeButton("No"){ dialogInterface, id ->
                findNavController().popBackStack()
            }

            show()
        }
    }

    private fun navigateToRecipeDetail(recipe: Recipe){

        val bundle = bundleOf("recipe" to recipe)
        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeDetailFragment, bundle)
    }
}























