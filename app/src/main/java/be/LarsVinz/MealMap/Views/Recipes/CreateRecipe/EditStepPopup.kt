package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EditStepPopup(context : Context, val recipeStep : RecipeStep?, val recipeStepList: MutableList<RecipeStep>) : AlertDialog(context) {

    private val addBtn : Button
    private val deleteBtn : ImageButton
    private val stepSpinner : Spinner
    private val stepExplanationTxt : EditText
    private val hasTimerSwitch : Switch
    private val timerLengthTxt : TextView

    private var timerLength = LocalTime.of(0, 0, 0)

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.edit_recipe_popup, null, false)
        setView(view)

        addBtn = view.findViewById(R.id.AddRecipePopupBtn)
        deleteBtn = view.findViewById(R.id.deleteBtn)
        stepSpinner = view.findViewById(R.id.stepNumberPopupSpr)
        stepExplanationTxt = view.findViewById(R.id.stepExplanationPopupTxt)
        hasTimerSwitch = view.findViewById(R.id.hasTimerSwitch)
        timerLengthTxt = view.findViewById(R.id.timerLengthTxt)

        populateFields(recipeStep)

        hasTimerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) openTimePickerPopup()
            else {
                setTimertext(false)
                timerLength = LocalTime.ofSecondOfDay(0)
            }
        }

        timerLengthTxt.setOnClickListener { openTimePickerPopup() }

        addBtn.setOnClickListener{
            saveRecipeStep()
            this.dismiss()
        }

        deleteBtn.setOnClickListener {
            deleteRecipe()
            this.dismiss()
        }
    }

    public fun saveRecipeStep() {

        val explanation = stepExplanationTxt.text.toString()

        val newRecipeStep = RecipeStep(explanation, timerLength.toSecondOfDay())
        val stepNumber = stepSpinner.selectedItem as Int

        recipeStepList.remove(recipeStep)
        recipeStepList.add(stepNumber - 1, newRecipeStep)
    }

    public fun deleteRecipe(){
        recipeStepList.remove(recipeStep)
    }

    private fun populateFields(recipeStep : RecipeStep?){

        fun setSpinner(numbers : Array<Int>, index : Int){
            val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, numbers)
            stepSpinner.adapter = spinnerAdapter
            stepSpinner.setSelection(index)
        }

        recipeStep?.let {

           val recipeStepIndex = recipeStepList.indexOf(recipeStep)
           val numbers = Array(recipeStepList.size) { it + 1 }
           setSpinner(numbers, recipeStepIndex)

           // explanation text
           stepExplanationTxt.setText(recipeStep.explanation)

           // switch and timer text

            timerLength = LocalTime.ofSecondOfDay(recipeStep.timerLength.toLong())

           if (timerLength.toSecondOfDay() == 0){
               hasTimerSwitch.isChecked = false
               setTimertext(false)
           }
           else{
               hasTimerSwitch.isChecked = true
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

            timerLengthTxt.text = timerLength.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            timerLengthTxt.visibility = View.VISIBLE

        } else{

            timerLengthTxt.visibility = View.GONE
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






























