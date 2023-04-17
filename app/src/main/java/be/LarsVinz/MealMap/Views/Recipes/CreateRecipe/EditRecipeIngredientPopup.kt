package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.isDigitsOnly
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.EditRecipeIngredientPopupBinding
import com.google.android.material.snackbar.Snackbar

class EditRecipeIngredientPopup(context : Context, private val ingredients : MutableList<Ingredient>) : AlertDialog(context), AdapterView.OnItemSelectedListener {

    private val binding = EditRecipeIngredientPopupBinding.inflate(layoutInflater)

    init {

        setIngredientSpinner()

        binding.saveBtn.setOnClickListener { onSaveButton() }
        binding.removeBtn.setOnClickListener { onDeleteButton() }

        setView(binding.root)
    }

    private fun setIngredientSpinner(){

        val ingredientNames = mutableListOf<String>()
        ingredients.forEach { ingredientNames.add(it.ingredientName) }
        ingredientNames.add("New")

        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, ingredientNames)
        binding.ingredientSpinner.adapter = spinnerAdapter
        binding.ingredientSpinner.setSelection(ingredientNames.size - 1)

        binding.ingredientSpinner.onItemSelectedListener = this
    }

    private fun onSaveButton(){


        val name = binding.ingredientNameETxt.text.toString()
        val amount = binding.ingredientAmountETxt.text.toString()
        val unit = binding.ingredientUnitETxt.text.toString()

        if (name.isBlank() || amount.isBlank() || unit.isBlank() || !amount.isDigitsOnly()){
            // TODO snackbar met error
            return
        }

        val index = binding.ingredientSpinner.selectedItemPosition
        if (index < ingredients.size) ingredients.removeAt(index)
        ingredients.add(index, Ingredient(name, amount.toInt(), unit))

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

            binding.ingredientNameETxt.setText(it.ingredientName)
            binding.ingredientAmountETxt.setText(it.amount.toString())
            binding.ingredientUnitETxt.setText(it.unit)
            binding.removeBtn.visibility = View.VISIBLE
        } ?: run {

            binding.ingredientNameETxt.setText("")
            binding.ingredientAmountETxt.setText("")
            binding.ingredientUnitETxt.setText("")
            binding.removeBtn.visibility = View.GONE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}