package be.LarsVinz.MealMap.Models.Data

@kotlinx.serialization.Serializable
data class RecipeStep(val explanation : String, val timerLength : Int) : java.io.Serializable