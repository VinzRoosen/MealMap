package be.LarsVinz.MealMap.Models

import android.content.Context
import androidx.core.content.edit
import be.LarsVinz.MealMap.Exceptions.CantLoadRecipeException
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import com.google.gson.*
import java.io.File

class RecipeRepository(val context : Context) : Repository<Recipe>{

    override fun save(toSave: Recipe) {

        val gson = Gson()
        val recipeGson = gson.toJson(toSave)

        val sharedPref =  context.getSharedPreferences(context.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(toSave.name, recipeGson)
            apply()
        }
    }

    override fun saveAll(toSaveList : List<Recipe>) {

        toSaveList.forEach { save(it) }
    }

    override fun load(toLoad: String): Recipe {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        val recipeData = sharedPref.getString(toLoad, null)

        recipeData?.let {

            val gson = Gson()
            return gson.fromJson(recipeData, Recipe::class.java)
        }

        throw CantLoadRecipeException("Can't load recipe with name '$toLoad'")
    }

    override fun loadAll(): List<Recipe> {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        val recipeMap = sharedPref.all
        val recipeList = mutableListOf<Recipe>()

        for (recipeData in recipeMap.entries){
            val gson = Gson()

            val recipe = gson.fromJson(recipeData.value as String, Recipe::class.java)
            recipeList.add(recipe)
        }
        return recipeList
    }

    override fun delete(toDelete: Recipe){

        val sharedPref = context.getSharedPreferences(context.getString(R.string.recipe_data), Context.MODE_PRIVATE)
        sharedPref.edit {
            remove(toDelete.name)
            commit()
        }

        toDelete.imagePath?.let { File(it).delete() }
    }

    override fun deleteAll(toDeleteList: List<Recipe>){

        toDeleteList.forEach { delete(it) }
    }
}