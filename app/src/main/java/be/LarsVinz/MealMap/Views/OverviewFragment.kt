package be.LarsVinz.MealMap.Views

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.navigation.fragment.findNavController
import be.LarsVinz.MealMap.Exceptions.CantLoadRecipeException
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipeRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.RecipePreviewFragment
import be.LarsVinz.MealMap.databinding.FragmentOverviewBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.random.nextInt

class OverviewFragment : Fragment(R.layout.fragment_overview), SensorEventListener{

    private lateinit var binding: FragmentOverviewBinding
    private lateinit var sensorManager: SensorManager

    private var amountOfShakes = 0
    private var inShake = false

    private var max = 0.0

    private var lastShakeTime : Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOverviewBinding.inflate(layoutInflater)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val recipeRepository = RecipeRepository(requireContext())

        // get names of last opened and last edited recipes
        val lastOpenedRecipeKey = recipeRepository.loadRecipeKey("last_opened")
        val lastEditedRecipeKey = recipeRepository.loadRecipeKey("last_edited")

        // set last opened and last edited recipes
        lastOpenedRecipeKey?.let { setLastOpenedRecipe(it) }
        lastEditedRecipeKey?.let { setLastEditedRecipe(it) }

        // set buttons
        binding.navToRecipesBtn.setOnClickListener { findNavController().navigate(R.id.action_global_recipeListFragment) }
        binding.navToShoppingListBtn.setOnClickListener { findNavController().navigate(R.id.action_global_shoppingListFragment) }

        return binding.root
    }

    private fun setLastOpenedRecipe(recipeName : String){

        try {

            val recipe = RecipeRepository(requireContext()).load(recipeName)

            val openedRecipeFragment = RecipePreviewFragment(recipe, false)
            childFragmentManager.beginTransaction().apply {
                replace(binding.lastOpenedRecipeFrame.id, openedRecipeFragment)
                commit()
            }

            binding.lastOpenedRecipeEmptyTxt.visibility = View.GONE
        }
        catch (_: CantLoadRecipeException){

        }
    }

    private fun setLastEditedRecipe(recipeName : String){

        try {

            val recipe = RecipeRepository(requireContext()).load(recipeName)

            val editedRecipeFragment = RecipePreviewFragment(recipe, false)
            childFragmentManager.beginTransaction().apply {
                replace(binding.lastEditedRecipeFrame.id, editedRecipeFragment)
                commit()
            }

            binding.lastEditedRecipeEmtyTxt.visibility = View.GONE
        }catch (_: CantLoadRecipeException){

        }
    }

    private fun setRandomRecipe(){

        // get a random recipe
        val allRecipes = RecipeRepository(requireContext()).loadAll()

        if (allRecipes.isEmpty()) return

        val randomIndex = Random.nextInt(allRecipes.size)
        val recipe = allRecipes[randomIndex]

        val randomRecipeFragment = RecipePreviewFragment(recipe, false)
        childFragmentManager.beginTransaction().apply {
            replace(binding.randomRecipeFrame.id, randomRecipeFragment)
            commit()
        }

        binding.randomRecipeEmptyTxt.visibility = View.GONE
    }


    override fun onSensorChanged(event: SensorEvent?) {

        event?.let {

            val totalAcceleration = (sqrt( it.values[0] * it.values[0] + it.values[1] * it.values[1] + it.values[2] * it.values[2] ) * 100).roundToInt() / 100.0

            val currentTime = System.currentTimeMillis()

            if (currentTime - lastShakeTime > 2000){
                amountOfShakes = 0 // reset
            }

            if (totalAcceleration <= 50) inShake = false

            if (totalAcceleration >= 60 && !inShake){

                amountOfShakes++
                lastShakeTime = currentTime
                inShake = true
            }

            if (amountOfShakes >= 5){
                setRandomRecipe()
                amountOfShakes = 0
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()

        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        // check if phone has accelerometer
        if (sensor == null) binding.randomRecipeEmptyTxt.text = "This phone does not have an accelerometer"
        else                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}