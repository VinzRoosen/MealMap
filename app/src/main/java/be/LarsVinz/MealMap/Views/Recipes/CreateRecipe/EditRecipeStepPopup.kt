package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.EditRecipeIngredientPopupBinding
import be.LarsVinz.MealMap.databinding.EditRecipeStepPopupBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EditRecipeStepPopup(context : Context, val recipeStep : RecipeStep?, val recipeStepList: MutableList<RecipeStep>) : AlertDialog(context) {

    private val binding = EditRecipeStepPopupBinding.inflate(layoutInflater)

    private var timerLength = LocalTime.of(0, 0, 0)

    init {

        populateFields(recipeStep)

        binding.hasTimerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) openTimePickerPopup()
            else {
                setTimertext(false)
                timerLength = LocalTime.ofSecondOfDay(0)
            }
        }

        binding.timerLengthTxt.setOnClickListener { openTimePickerPopup() }

        binding.AddRecipePopupBtn.setOnClickListener{
            saveRecipeStep()
            this.dismiss()
        }

        binding.deleteBtn.setOnClickListener {
            deleteRecipe()
            this.dismiss()
        }

        setView(binding.root)
    }

    fun saveRecipeStep() {

        val explanation = binding.stepExplanationPopupTxt.text.toString()

        val newRecipeStep = RecipeStep(explanation, timerLength.toSecondOfDay())
        val stepNumber = binding.stepNumberPopupSpr.selectedItem as Int

        recipeStepList.remove(recipeStep)
        recipeStepList.add(stepNumber - 1, newRecipeStep)
    }

    fun deleteRecipe(){
        recipeStepList.remove(recipeStep)
    }

    private fun populateFields(recipeStep : RecipeStep?){

        fun setSpinner(numbers : Array<Int>, index : Int){
            val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, numbers)
            binding.stepNumberPopupSpr.adapter = spinnerAdapter
            binding.stepNumberPopupSpr.setSelection(index)
        }

        recipeStep?.let {

           val recipeStepIndex = recipeStepList.indexOf(recipeStep)
           val numbers = Array(recipeStepList.size) { it + 1 }
           setSpinner(numbers, recipeStepIndex)

           // explanation text
           binding.stepExplanationPopupTxt.setText(recipeStep.explanation)

           // switch and timer text
            timerLength = LocalTime.ofSecondOfDay(recipeStep.timerLength.toLong())

           if (timerLength.toSecondOfDay() == 0){
               binding.hasTimerSwitch.isChecked = false
               setTimertext(false)
           }
           else{
               binding.hasTimerSwitch.isChecked = true
               setTimertext(true)
           }
        } ?: run {

            val numbers = Array(recipeStepList.size + 1) { it + 1 }
            setSpinner(numbers, recipeStepList.size)

            setTimertext(false)
        }
    }

    private fun setTimertext(visable : Boolean){

        if (visable)  {

            binding.timerLengthTxt.text = timerLength.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            binding.timerLengthTxt.visibility = View.VISIBLE

        } else{

            binding.timerLengthTxt.visibility = View.GONE
        }
    }

    private fun openTimePickerPopup(){
        val timePickerPopup = TimePickerPopup(context)
        timePickerPopup.show()
        timePickerPopup.setCanceledOnTouchOutside(false)

        timePickerPopup.setSelectedTime(timerLength)

        timePickerPopup.setOnAddBtnListener {
            timerLength = timePickerPopup.getSelectedTime()
            setTimertext(true)
            timePickerPopup.dismiss()
        }
    }
}






























