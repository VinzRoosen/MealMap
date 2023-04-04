package be.LarsVinz.MealMap.Models

import be.LarsVinz.MealMap.Models.Data.Recipe

interface RecipeRepository {

    fun saveRecipe(recipe : Recipe)
    fun saveRecipes(recipes : List<Recipe>)

    fun loadRecipe(recipeName : String) : Recipe
    fun loadAllRecipes() : List<Recipe>
}