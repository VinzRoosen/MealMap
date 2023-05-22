package be.LarsVinz.MealMap.Views.ShoppingList

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.Navigation.findNavController
import be.LarsVinz.MealMap.Enums.RecipeUnit
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.AddShoppingListPopupBinding
import com.google.android.material.snackbar.Snackbar
import java.time.Duration

class AddShoppingListPopup(
    context: Context,
    val shoppingList: MutableList<Ingredient>,
    val view: View
) : AlertDialog(context) {

    private val binding = AddShoppingListPopupBinding.inflate(layoutInflater)

    init {
        makeInvisible()
        setRecipeUnitSpinner()

        binding.btnFromRecipe.setOnClickListener { onFromRecipeList() }
        binding.btnSingleGrocery.setOnClickListener { onSingleGrocery() }

        setView(binding.root)
    }

    private fun onSingleGrocery() {
        makeVisible()
        binding.btnSingleGrocery.text = context.getString(R.string.Save)
        binding.btnFromRecipe.text = context.getString(R.string.Cancel)

        binding.btnSingleGrocery.setOnClickListener {
            val ingredientNameText = binding.ingredientNameETxt.text.toString()
            val ingredientAmountText = binding.ingredientAmountETxt.text.toString()

            if (ingredientNameText.isNotBlank() && ingredientAmountText.isNotBlank()) {
                val unit = RecipeUnit.values()[binding.ingredientUnitSpr.selectedItemPosition]
                shoppingList.add(Ingredient(ingredientNameText, ingredientAmountText.toDouble(), unit))
                dismiss()

            } else {
                if (ingredientNameText.isBlank()) {
                    binding.ingredientNameETxt.setHintTextColor(Color.RED)
                }
                if (ingredientAmountText.isBlank()) {
                    binding.ingredientAmountETxt.setHintTextColor(Color.RED)
                }
            }
        }

        binding.btnFromRecipe.setOnClickListener{ dismiss() }
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

    private fun setRecipeUnitSpinner() {

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