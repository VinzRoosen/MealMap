package be.LarsVinz.MealMap

@kotlinx.serialization.Serializable
data class RecipeStep(val explanation : String, val timerLength : Int) : java.io.Serializable