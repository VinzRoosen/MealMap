package be.LarsVinz.MealMap

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class EditStepDialog(context : Context, val recipeStep : RecipeStep?, val recipeStepList: MutableList<RecipeStep>) : AlertDialog(context) {

    private val view : View

    private val addBtn : Button
    private val deleteBtn : ImageButton
    private val stepSpinner : Spinner
    private val stepExplanationTxt : EditText
    private val hasTimerSwitch : Switch
    private val timerLengthTxt : TextView

    init {
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.edit_recipe_popup, null, false)
        setView(view)

        addBtn = view.findViewById(R.id.AddRecipePopupBtn)
        deleteBtn = view.findViewById(R.id.deleteBtn)
        stepSpinner = view.findViewById(R.id.stepNumberPopupSpr)
        stepExplanationTxt = view.findViewById(R.id.stepExplanationPopupTxt)
        hasTimerSwitch = view.findViewById(R.id.hasTimerSwitch)
        timerLengthTxt = view.findViewById(R.id.timerLengthTxt)

        populateFields(recipeStep)

        hasTimerSwitch.setOnCheckedChangeListener { buttonView, isChecked -> setTimerVisibility(isChecked) }
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
        val timerLength = kotlin.run {
            if (hasTimerSwitch.isChecked) timerLengthTxt.text.toString().toInt() else 0
        }

        val newRecipeStep = RecipeStep(explanation, timerLength)
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
           if (recipeStep.timerLength == 0){
               hasTimerSwitch.isChecked = false
               setTimerVisibility(false)
           }
           else{
               hasTimerSwitch.isChecked = true
               setTimerVisibility(true)
               timerLengthTxt.text = recipeStep.timerLength.toString()
           }
        } ?: run {

            val numbers = Array(recipeStepList.size + 1) { it + 1 }
            setSpinner(numbers, recipeStepList.size)

            setTimerVisibility(false)
        }
    }

    private fun setTimerVisibility(status : Boolean){

        if (status)  timerLengthTxt.visibility = View.VISIBLE else timerLengthTxt.visibility = View.GONE
    }
}






























