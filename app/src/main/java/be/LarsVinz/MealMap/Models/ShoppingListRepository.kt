package be.LarsVinz.MealMap.Models

import android.content.Context
import androidx.core.content.edit
import be.LarsVinz.MealMap.Exceptions.CantLoadRecipeException
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import com.google.gson.Gson

class ShoppingListRepository(val context: Context) : Repository<Ingredient> {
    private val gson = Gson()

    fun saveIngredientsFromRecipes(recipeList: List<Recipe>) {
        recipeList.forEach {
            it.ingredients.forEach { ingredient -> save(ingredient) }
        }
    }

    override fun save(toSave: Ingredient) {
        val ingredientGson = gson.toJson(toSave)

        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.shopping_data),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putString(toSave.name, ingredientGson)
            apply()
        }
    }

    override fun saveAll(toSaveList: List<Ingredient>) {
        toSaveList.forEach { save(it) }
    }

    override fun load(toLoad: String): Ingredient {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        val recipeData = sharedPref.getString(toLoad, null)

        recipeData?.let {

            val gson = Gson()
            return gson.fromJson(recipeData, Ingredient::class.java)
        }

        throw CantLoadRecipeException("Can't load recipe with name '$toLoad'")
    }

    override fun loadAll(): List<Ingredient> {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.shopping_data),
            Context.MODE_PRIVATE
        )
        val ingredientMap = sharedPref.all

        return ingredientMap.entries.map { entry ->
            gson.fromJson(entry.value as String, Ingredient::class.java)
        }
    }


    override fun delete(toDelete: Ingredient) {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.shopping_data),
            Context.MODE_PRIVATE
        )
        sharedPref.edit {
            remove(toDelete.name)
            commit()
        }
    }

    override fun deleteAll(toDeleteList: List<Ingredient>) {
        toDeleteList.forEach { delete(it) }
    }


}