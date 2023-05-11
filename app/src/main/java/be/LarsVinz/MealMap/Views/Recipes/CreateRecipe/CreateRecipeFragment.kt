package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentCreateRecipeBinding
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CreateRecipeFragment : Fragment(R.layout.fragment_create_recipe) {

    private lateinit var binding: FragmentCreateRecipeBinding
    private lateinit var pictureActivityResult: ActivityResultLauncher<Uri>

    private var previousRecipe : Recipe? = null

    private val ingredientList = mutableListOf<Ingredient>()
    private val recipeStepList = mutableListOf<RecipeStep>()
    private val recipeTagList = mutableListOf<Tag>()

    private var previousImage : File? = null
    private var recipeImage : File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(layoutInflater)

        pictureActivityResult = registerForActivityResult(ActivityResultContracts.TakePicture()){ succes ->

            if (succes){

                val image = BitmapFactory.decodeFile(recipeImage!!.path)
                val scaledImage = Bitmap.createScaledBitmap(image, 350, 350, false)

                FileOutputStream(recipeImage).use {
                    scaledImage.compress(Bitmap.CompressFormat.JPEG, 80, it)
                    it.flush()
                    it.close()
                }
            }
            else{

                recipeImage = previousImage
                Toast.makeText(requireContext(), "Image has not been saved", Toast.LENGTH_LONG).show()
            }
        }

        previousRecipe = arguments?.getSerializable("recipe") as Recipe?
        previousRecipe?.let {

            ingredientList.addAll(it.ingredients)
            recipeStepList.addAll(it.steps)
            recipeTagList.addAll(it.tags)
            it.imagePath?.let {path -> previousImage = File(path) }

            binding.recipeNameTxt.setText(it.name)
        }

        val recipeFragment = RecipeFragment(true, recipeStepList, ingredientList)
        recipeFragment.setRecipeData(ingredientList, recipeStepList)

        openFragment(recipeFragment)

        setClickEvents()

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
        // bronnen:
        //      https://stackoverflow.com/questions/61941959/activityresultcontracts-takepicture
        //      https://developer.android.com/training/secure-file-sharing/setup-sharing

        val dir =  File(requireContext().filesDir, "recipe_images")
        if (!dir.exists())  dir.mkdirs()

        val imageFile = File(dir, getImagePath())
        val uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".provider", imageFile)

        recipeImage = imageFile
        pictureActivityResult.launch(uri)
    }

    private fun saveRecipe() : Recipe? {

        // get the recipe name
        val recipeName = binding.recipeNameTxt.text.toString()
        if (recipeName.isBlank()){
            Toast.makeText(requireContext(), "The recipe needs a name!", Toast.LENGTH_LONG).show()
            return null
        }

        previousRecipe?.let { previousRecipe ->

            // delete previous recipe if name has changed
            if (recipeName.lowercase() != previousRecipe.name.lowercase()){
                RecipePreferencesRepository(requireContext()).deleteRecipe(previousRecipe)
            }
        }

        // delete image if there is a new picture
        recipeImage?.let {
            previousImage?.delete()
        }

        // create recipe
        val recipe = Recipe(recipeName, recipeStepList, ingredientList, recipeTagList, recipeImage?.path)

        // save recipe
        RecipePreferencesRepository(requireActivity()).saveRecipe(recipe)

        return recipe
    }

    fun openSaveConfirmationDialog(){

        AlertDialog.Builder(requireContext()).apply {

            setTitle("Save?")
            setMessage("Do you want to save this recipe?")
            setIcon(android.R.drawable.ic_dialog_alert)

            setPositiveButton("Yes") { dialogInterface, id ->
                val recipe =  saveRecipe()
                navigateToRecipeDetail(recipe!!)
            }

            setNegativeButton("No"){ dialogInterface, id ->
                recipeImage?.delete()
                findNavController().popBackStack()
            }

            show()
        }
    }

    private fun getImagePath() : String{
        val dateTime = LocalDateTime.now()
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
    }

    private fun navigateToRecipeDetail(recipe: Recipe){

        val bundle = bundleOf("recipe" to recipe)
        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeDetailFragment, bundle)
    }
}























