package be.LarsVinz.MealMap.Models

import android.content.Context
import androidx.core.content.edit
import be.LarsVinz.MealMap.Exceptions.CantLoadRecipeException
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import com.google.gson.Gson

class ShoppingListRepository(val context : Context){
    private val gson = Gson()
    fun saveIngredient(ingredient: Ingredient) {
        val ingredientGson = gson.toJson(ingredient)

        val sharedPref =  context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(ingredient.name, ingredientGson)
            apply()
        }
    }

    fun saveIngredientsFromRecipes(recipeList: List<Recipe>){
        recipeList.forEach{it.ingredients.forEach{ ingredient -> saveIngredient(ingredient)}}
    }

    fun loadAllIngredients(): List<Ingredient> {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        val ingredientMap = sharedPref.all

        return ingredientMap.entries.map { entry ->
            gson.fromJson(entry.value as String, Ingredient::class.java)
        }
    }

    private fun deleteIngredient(ingredient: Ingredient){

        val sharedPref = context.getSharedPreferences(context.getString(R.string.shopping_data), Context.MODE_PRIVATE)
        sharedPref.edit {
            remove(ingredient.name)
            commit()
        }
    }

    fun deleteIngredients(ingredients: List<Ingredient>){

        ingredients.forEach { deleteIngredient(it) }
    }


}