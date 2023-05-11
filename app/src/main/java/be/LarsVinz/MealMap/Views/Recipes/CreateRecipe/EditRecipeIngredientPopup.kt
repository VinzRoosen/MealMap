package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.R
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.ui.text.toLowerCase
import androidx.core.text.isDigitsOnly
import be.LarsVinz.MealMap.Enums.RecipeUnit
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.databinding.EditRecipeIngredientPopupBinding
import java.util.*

class EditRecipeIngredientPopup(context : Context, private val ingredients : MutableList<Ingredient>, private val multiplier : Int) : AlertDialog(context), AdapterView.OnItemSelectedListener {

    private val binding = EditRecipeIngredientPopupBinding.inflate(layoutInflater)

    init {

        setIngredientSpinner()
        setRecipeUnitSpinner()

        binding.saveBtn.setOnClickListener { onSaveButton() }
        binding.removeBtn.setOnClickListener { onDeleteButton() }

        setView(binding.root)
    }

    private fun setIngredientSpinner(){

        val ingredientNames = mutableListOf<String>()
        ingredients.forEach { ingredientNames.add(it.name) }
        ingredientNames.add("New")

        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, ingredientNames)
        binding.ingredientSpinner.adapter = spinnerAdapter
        binding.ingredientSpinner.setSelection(ingredientNames.size - 1)

        binding.ingredientSpinner.onItemSelectedListener = this
    }

    private fun setRecipeUnitSpinner(){

        val recipeUnits = mutableListOf<String>()
        for (unit in RecipeUnit.values()) { recipeUnits.add(unit.name.lowercase()) }

        val spinnerAdapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, recipeUnits)
        binding.ingredientUnitSpr.adapter = spinnerAdapter
    }

    private fun onSaveButton(){

        val name = binding.ingredientNameETxt.text.toString()
        val amount = binding.ingredientAmountETxt.text.toString()
        val unit = RecipeUnit.values().first { it.ordinal == binding.ingredientUnitSpr.selectedItemPosition }

        if (name.isBlank() || amount.isBlank()){
            // TODO snackbar met error
            return
        }

        val index = binding.ingredientSpinner.selectedItemPosition
        if (index < ingredients.size) ingredients.removeAt(index)
        ingredients.add(index, Ingredient(name, amount.toInt() / multiplier, unit))
        ingredients.sortBy { it.name }
        this.dismiss()
    }

    private fun onDeleteButton(){

        val index = binding.ingredientSpinner.selectedItemPosition
        if (index < ingredients.size) ingredients.removeAt(index)

        this.dismiss()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        val selectedIngredient = ingredients.getOrNull(pos)

        selectedIngredient?.let {

            binding.ingredientNameETxt.setText(it.name)
            binding.ingredientAmountETxt.setText((it.amount * multiplier).toString())
            binding.ingredientUnitSpr.setSelection(it.unit.ordinal)
            binding.removeBtn.visibility = View.VISIBLE

        } ?: run {

            binding.ingredientNameETxt.setText("")
            binding.ingredientAmountETxt.setText("")
            binding.removeBtn.visibility = View.GONE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}