package be.LarsVinz.OurApp

@kotlinx.serialization.Serializable
data class RecipeStep(val explanation : String, val timerLength : Int) : java.io.Serializable