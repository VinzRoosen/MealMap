package be.LarsVinz.MealMap.Models.DataClasses

@kotlinx.serialization.Serializable
data class Ingredient(val ingredientName : String, val amount : Int, val unit: String) : java.io.Serializable

