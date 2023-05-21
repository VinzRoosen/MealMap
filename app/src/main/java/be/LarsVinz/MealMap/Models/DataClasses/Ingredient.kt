package be.LarsVinz.MealMap.Models.DataClasses

import be.LarsVinz.MealMap.Enums.RecipeUnit

@kotlinx.serialization.Serializable
data class Ingredient(val name: String, var amount: Double, val unit: RecipeUnit) : java.io.Serializable

