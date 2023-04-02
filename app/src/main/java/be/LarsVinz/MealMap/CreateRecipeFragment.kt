package be.LarsVinz.MealMap

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.databinding.FragmentCreateRecipeBinding


class CreateRecipeFragment : Fragment(R.layout.fragment_create_recipe) {

    private lateinit var binding: FragmentCreateRecipeBinding

    private lateinit var adapter: RecipeStepAdaptor
    private val recipeStepList = mutableListOf<RecipeStep>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(layoutInflater)

        val recipe = arguments?.getSerializable("recipe") as Recipe?

        recipe?.let {
            binding.recipeNameTxt.setText(it.recipeName)
            recipeStepList.addAll(it.steps)
        }

        adapter = RecipeStepAdaptor(recipeStepList, requireContext()) {recipeStep -> // This is the OnItemClick
            openEditStepDialog(recipeStep)
        }

        binding.recipeStepRvw.adapter = adapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)

        binding.addRecipeBtn.setOnClickListener {onAddRecipeStepBtn()}
        binding.saveRecipeBtn.setOnClickListener {onSaveRecipeBtn()}

        return binding.root
    }

    private fun onAddRecipeStepBtn(){
        openEditStepDialog(null)
    }

    private fun onSaveRecipeBtn(){
        val recipe = Recipe(binding.recipeNameTxt.text.toString(), recipeStepList)

        // TODO: save het recept
        val repository = RecipeRepository(requireActivity())
        repository.save(recipe)

        val bundle = bundleOf("recipe" to recipe)
        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeDetailFragment, bundle)
    }

    private fun openEditStepDialog(recipeStep : RecipeStep?){

        val dialogBuilder = AlertDialog.Builder(this.context)
        val createRecipePopupView = layoutInflater.inflate(R.layout.edit_recipe_popup, null)

        dialogBuilder.setView(createRecipePopupView)
        val dialog = dialogBuilder.create()
        dialog.show()

        val addBtn = createRecipePopupView.findViewById<Button>(R.id.AddRecipePopupBtn)
        val stepSpinner = createRecipePopupView.findViewById<Spinner>(R.id.stepNumberPopupSpr)
        val stepExplanationTxt = createRecipePopupView.findViewById<TextView>(R.id.stepExplanationPopupTxt)
        val hasTimerSwitch = createRecipePopupView.findViewById<Switch>(R.id.hasTimerSwitch)
        val timerLengthTxt = createRecipePopupView.findViewById<TextView>(R.id.timerLengthTxt)

        var recipeStepIndex = recipeStepList.size

        if (recipeStep != null)
        {   // edit an existing step --> load the step data in the correct fields
            recipeStepIndex = recipeStepList.indexOf(recipeStep)
            recipeStepList.removeAt(recipeStepIndex)

            stepExplanationTxt.text = recipeStep.explanation

            if (recipeStep.timerLength == 0){
                hasTimerSwitch.isChecked = false
            }
            else{
                hasTimerSwitch.isChecked = true
                timerLengthTxt.text = recipeStep.timerLength.toString()
            }
        }

        val numbers = Array(recipeStepList.size + 1) { it + 1 }
        val spinnerAdapter = ArrayAdapter<Int>(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, numbers)
        stepSpinner.adapter = spinnerAdapter
        stepSpinner.setSelection(recipeStepIndex)

        if (hasTimerSwitch.isChecked)  timerLengthTxt.visibility = View.VISIBLE else timerLengthTxt.visibility = View.GONE

        hasTimerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked)  timerLengthTxt.visibility = View.VISIBLE else timerLengthTxt.visibility = View.GONE
        }

        addBtn.setOnClickListener{

            dialog.dismiss()
        }

        dialog.setOnDismissListener {

            val explanation = stepExplanationTxt.text.toString()
            val timerLength = kotlin.run {
                if (hasTimerSwitch.isChecked) timerLengthTxt.text.toString().toInt() else 0
            }

            val newRecipeStep = RecipeStep(explanation, timerLength)
            val stepNumber = stepSpinner.selectedItem as Int

            recipeStepList.add(stepNumber - 1, newRecipeStep)
            adapter.notifyDataSetChanged()
        }
    }
}