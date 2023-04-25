package be.LarsVinz.MealMap.Models.DataClasses

import be.LarsVinz.MealMap.Enums.Tag

@kotlinx.serialization.Serializable
data class Recipe(val name : String, val steps : List<RecipeStep>, val ingredients : List<Ingredient>, val tags : List<Tag>) : java.io.Serializable
