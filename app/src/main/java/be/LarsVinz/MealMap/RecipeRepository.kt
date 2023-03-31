package be.LarsVinz.MealMap

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.gson.*

class RecipeRepository(val activity : FragmentActivity) { // TODO: activity meegeven vind ik niet leuk

    public fun save(recipe : Recipe) {

        val gson = Gson()
        val recipeGson = gson.toJson(recipe)

        val sharedPref =  activity.getSharedPreferences(activity.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(recipe.recipeName, recipeGson)
            apply()
        }
    }

    public fun loadAll() : List<Recipe> {

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