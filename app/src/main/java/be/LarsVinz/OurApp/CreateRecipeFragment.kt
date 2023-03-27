package be.LarsVinz.OurApp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.OurApp.databinding.FragmentCreateRecipeBinding

class CreateRecipeFragment : Fragment(R.layout.fragment_create_recipe) {

    private lateinit var binding: FragmentCreateRecipeBinding

    private lateinit var adapter: RecipeStepAdaptor
    private val recipeStepList = mutableListOf<RecipeStep>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(layoutInflater)

        adapter = RecipeStepAdaptor(recipeStepList, true)
        binding.recipeStepRvw.adapter = adapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)

        binding.addRecipeBtn.setOnClickListener {onAddRecipeBtn()}
        binding.saveRecipeBtn.setOnClickListener {onSaveRecipeBtn()}

        return binding.root
    }

    private fun onAddRecipeBtn(){
        editStepDialog()


    }

    private fun onSaveRecipeBtn(){
        val recipe = Recipe(binding.recipeNameTxt.text.toString(), recipeStepList)

        // TODO: save het recept

        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeListFragment)
    }

    private fun editStepDialog(){
        val dialogBuilder = AlertDialog.Builder(this.context)
        val createRecipePopupView = layoutInflater.inflate(R.layout.edit_recipe_popup, null)

        dialogBuilder.setView(createRecipePopupView)
        val dialog = dialogBuilder.create()
        dialog.show()

        val addBtn = createRecipePopupView.findViewById<Button>(R.id.AddRecipePopupBtn)
        val stepSpinner = createRecipePopupView.findViewById<Spinner>(R.id.stepNumberPopupSpr)
        val stepExplanationTxt = createRecipePopupView.findViewById<TextView>(R.id.stepExplanationPopupTxt)

        val numbers = Array(recipeStepList.size + 1) { it + 1 }
        val spinnerAdapter = ArrayAdapter<Int>(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, numbers)
        stepSpinner.adapter = spinnerAdapter;
        stepSpinner.setSelection(numbers.size - 1)

        addBtn.setOnClickListener{

            val recipeStep = RecipeStep(stepExplanationTxt.text.toString(), 0)
            val stepNumber = stepSpinner.selectedItem as Int

            recipeStepList.add(stepNumber - 1, recipeStep)
            adapter.notifyDataSetChanged()

            dialog.dismiss()
        }


    }
}