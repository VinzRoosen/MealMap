package be.LarsVinz.MealMap.Views.ShoppingList

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.Navigation.findNavController
import be.LarsVinz.MealMap.Enums.RecipeUnit
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.AddShoppingListPopupBinding

class AddShoppingListPopup(context: Context, val view: View) : AlertDialog(context) {

    private val binding = AddShoppingListPopupBinding.inflate(layoutInflater)
    private val shoppingListRepository = ShoppingListRepository(context)

    init {
        makeInvisible()
        setRecipeUnitSpinner()

        binding.btnFromRecipe.setOnClickListener { onFromRecipeList() }
        binding.btnSingleGrocery.setOnClickListener { onSingleGrocery() }

        setView(binding.root)
    }

    private fun onSingleGrocery() {
        makeVisible()
        binding.btnSingleGrocery.text = "Save \n"
        binding.btnFromRecipe.isEnabled = false

        binding.btnSingleGrocery.setOnClickListener {
            val ingredientName = binding.ingredientNameETxt.text.toString()
            val ingredientAmount = binding.ingredientAmountETxt.text.toString()

            if (ingredientName.isNotBlank() || ingredientAmount.isNotBlank()) {
                val unit = RecipeUnit.values()[binding.ingredientUnitSpr.selectedItemPosition]
                val ingredient = Ingredient(ingredientName, ingredientAmount.toInt(), unit)
                shoppingListRepository.saveIngredient(ingredient)
            }
            dismiss()
        }
    }

    private fun onFromRecipeList() {
        val bundle = Bundle().apply {
            putString("case", "select_shopping_list")
        }
        findNavController(view).navigate(
            R.id.action_shoppingListFragment_to_selectRecipeFragment,
            bundle
        )
        dismiss()
    }

    private fun setRecipeUnitSpinner() { //TODO: zelfde als EDITRecipeIngredient POPUP

        val recipeUnits = mutableListOf<String>()
        for (unit in RecipeUnit.values()) {
            recipeUnits.add(unit.name.lowercase())
        }

        val spinnerAdapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, recipeUnits)
        binding.ingredientUnitSpr.adapter = spinnerAdapter
    }

    private fun makeInvisible() {
        binding.ingredientNameETxt.visibility = View.INVISIBLE
        binding.ingredientAmountETxt.visibility = View.INVISIBLE
        binding.ingredientUnitSpr.visibility = View.INVISIBLE
    }

    private fun makeVisible() {
        binding.ingredientNameETxt.visibility = View.VISIBLE
        binding.ingredientAmountETxt.visibility = View.VISIBLE
        binding.ingredientUnitSpr.visibility = View.VISIBLE
    }
}