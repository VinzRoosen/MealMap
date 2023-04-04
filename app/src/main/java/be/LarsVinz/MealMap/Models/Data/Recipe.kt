package be.LarsVinz.MealMap.Models.Data

@kotlinx.serialization.Serializable
data class Recipe(val recipeName : String, val steps : List<RecipeStep>) : java.io.Serializable
