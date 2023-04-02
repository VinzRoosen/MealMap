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

        binding.addRecipeBtn.setOnClickListener  { openEditStepDialog(null) }
        binding.saveRecipeBtn.setOnClickListener { onSaveRecipeBtn() }

        return binding.root
    }

    private fun onSaveRecipeBtn(){
        val recipe = Recipe(binding.recipeNameTxt.text.toString(), recipeStepList)

        val repository = RecipeRepository(requireActivity())
        repository.save(recipe)

        val bundle = bundleOf("recipe" to recipe)
        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeDetailFragment, bundle)
    }

    private fun openEditStepDialog(recipeStep : RecipeStep?){

        val editStepDialog = EditStepDialog(requireContext(), recipeStep, recipeStepList)
        editStepDialog.show()

        editStepDialog.setOnDismissListener {

            editStepDialog.saveRecipeStep()

            adapter.notifyDataSetChanged()
        }
    }
}