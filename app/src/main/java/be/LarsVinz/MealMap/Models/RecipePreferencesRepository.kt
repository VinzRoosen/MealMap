package be.LarsVinz.MealMap.Models

import android.content.Context
import androidx.fragment.app.FragmentActivity
import be.LarsVinz.MealMap.Exceptions.CantLoadRecipeException
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Models.Data.Recipe
import com.google.gson.*

class RecipePreferencesRepository(val activity : FragmentActivity) : RecipeRepository{ // TODO: activity meegeven vind ik niet leuk

    override fun saveRecipe(recipe: Recipe) {

        val gson = Gson()
        val recipeGson = gson.toJson(recipe)

        val sharedPref =  activity.getSharedPreferences(activity.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {            putString(recipe.recipeName, recipeGson)
            apply()
        }
    }

    override fun saveRecipes(recipes : List<Recipe>) {

        recipes.forEach { saveRecipe(it) }
    }

    override fun loadRecipe(recipeName: String): Recipe {

        val sharedPref = activity.getSharedPreferences(activity.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        val recipeData = sharedPref.getString(recipeName, null)

        recipeData?.let {

            val gson = Gson()
            val recipe = gson.fromJson(recipeData, Recipe::class.java)
            return recipe
        }

        throw CantLoadRecipeException("Can't load recipe with name '$recipeName'")
    }

    override fun loadAllRecipes(): List<Recipe> {

        val sharedPref = activity.getSharedPreferences(activity.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        val recipeMap = sharedPref.all
        val recipeList = mutableListOf<Recipe>()

        for (recipeData in recipeMap.entries){
            val gson = Gson()

            val recipe = gson.fromJson(recipeData.value as String, Recipe::class.java)
            recipeList.add(recipe)
        }
        return recipeList
    }
}