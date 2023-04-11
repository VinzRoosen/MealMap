package be.LarsVinz.MealMap.Models.DataClasses

@kotlinx.serialization.Serializable
data class Recipe(val name : String, val steps : List<RecipeStep>, val ingredients : List<Ingredient>) : java.io.Serializable
