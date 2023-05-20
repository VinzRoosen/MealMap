package be.LarsVinz.MealMap.Models

import be.LarsVinz.MealMap.Models.DataClasses.Recipe

class RecipeFilter {
        fun filterRecipes(recipeList: List<Recipe>, searchText: CharSequence?): List<Recipe> {
            val filteredList: MutableList<Recipe> = mutableListOf()
            for (item: Recipe in recipeList) {
                if (item.name.lowercase().contains(searchText.toString().lowercase()) || item.tags.toString().lowercase().contains(searchText.toString().lowercase())) {
                    filteredList.add(item)
                }
            }
            return filteredList
        }
    }

