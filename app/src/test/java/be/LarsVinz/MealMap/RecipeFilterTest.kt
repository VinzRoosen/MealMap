import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipeFilter
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeFilterTest {

    @Test
    fun `filterRecipes should return filtered list based on search text`() {

        // Arrange
        val recipeList = listOf(
            Recipe("Pasta", listOf() , listOf(), listOf(Tag.PASTA, Tag.BBQ), null),
            Recipe("Pizza", listOf() , listOf(), listOf(Tag.HEALTHY, Tag.GLUTEN_FREE), null),
            Recipe("Salad", listOf() , listOf(), listOf(Tag.LUNCH, Tag.BBQ), null),
        )

        val recipeFilter = RecipeFilter()

        // Act
        val filteredRecipes1 = recipeFilter.filterRecipes(recipeList, "Pasta")
        val filteredRecipes2 = recipeFilter.filterRecipes(recipeList, "healthy")
        val filteredRecipes3 = recipeFilter.filterRecipes(recipeList, "p")
        val filteredRecipes4 = recipeFilter.filterRecipes(recipeList, "BBQ")
        val filteredRecipes5 = recipeFilter.filterRecipes(recipeList, "nonexistent")


        // Assert
        assertEquals(1, filteredRecipes1.size)
        assertEquals("Pasta", filteredRecipes1[0].name)

        assertEquals(1, filteredRecipes2.size)
        assertEquals("Pizza", filteredRecipes2[0].name)

        assertEquals(2, filteredRecipes3.size)
        assertEquals("Pasta" , filteredRecipes3[0].name)
        assertEquals("Pizza", filteredRecipes3[1].name)

        assertEquals(2, filteredRecipes4.size)
        assertEquals("Pasta", filteredRecipes4[0].name)
        assertEquals("Salad", filteredRecipes4[1].name)

        assertEquals(0, filteredRecipes5.size)
    }
}


