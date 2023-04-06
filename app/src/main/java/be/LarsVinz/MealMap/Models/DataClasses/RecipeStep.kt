package be.LarsVinz.MealMap.Models.DataClasses

@kotlinx.serialization.Serializable
data class RecipeStep(val explanation : String, val timerLength : Int) : java.io.Serializable