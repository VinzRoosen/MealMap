package be.LarsVinz.MealMap.Models

import android.content.Context
import androidx.core.content.edit
import be.LarsVinz.MealMap.Exceptions.CantLoadRecipeException
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import com.google.gson.Gson

class ShoppingListRepository(val context : Context) : RecipeRepository {
    //TODO helemaal hetzelfde als recipeRepository alleen opgeslagen op een andere plaats
    override fun saveRecipe(recipe: Recipe) {

        val gson = Gson()
        val recipeGson = gson.toJson(recipe)

        val sharedPref =  context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(recipe.name, recipeGson)
            apply()
        }
    }

    override fun saveRecipes(recipes : List<Recipe>) {

        recipes.forEach { saveRecipe(it) }
    }

    override fun loadRecipe(recipeName: String): Recipe {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        val recipeData = sharedPref.getString(recipeName, null)

        recipeData?.let {

            val gson = Gson()
            return gson.fromJson(recipeData, Recipe::class.java)
        }

        throw CantLoadRecipeException("Can't load recipe with name '$recipeName'")
    }

    override fun loadAllRecipes(): List<Recipe> {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        val recipeMap = sharedPref.all
        val recipeList = mutableListOf<Recipe>()

        for (recipeData in recipeMap.entries){
            val gson = Gson()

            val recipe = gson.fromJson(recipeData.value as String, Recipe::class.java)
            recipeList.add(recipe)
        }
        return recipeList
    }

    override fun deleteRecipe(recipe: Recipe){

        val sharedPref = context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        sharedPref.edit {
            remove(recipe.name)
            commit()
        }

        recipe.imagePath?.let { ImageFileRepository(context).deleteImageRecipe(it) }
    }

    override fun deleteRecipes(recipes: List<Recipe>){

        recipes.forEach { deleteRecipe(it) }
    }

    fun loadAllIngredients(): List<Ingredient>{
        var recipes = listOf<Recipe>()
        recipes = loadAllRecipes()
        val ingredients = mutableListOf<Ingredient>()
        for (recipe in recipes){
            ingredients.addAll(recipe.ingredients)
        }
        return ingredients
    }
}