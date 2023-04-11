package be.LarsVinz.MealMap.Models.DataClasses

import be.LarsVinz.MealMap.Enums.RecipeUnit

@kotlinx.serialization.Serializable
data class Ingredient(val name : String, val amount : Int, val unit: RecipeUnit) : java.io.Serializable
