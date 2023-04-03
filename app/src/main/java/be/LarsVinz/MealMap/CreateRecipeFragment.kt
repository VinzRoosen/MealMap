package be.LarsVinz.MealMap

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
        binding.saveRecipeBtn.setOnClickListener { saveRecipeAndClose() }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val builder = AlertDialog.Builder(requireContext()).apply {
                    setTitle("Save?")
                    setMessage("Do you want to save this recipe?")
                    setIcon(android.R.drawable.ic_dialog_alert)

                    setPositiveButton("Save") { dialogInterface, id ->
                        saveRecipeAndClose()
                    }

                    setNegativeButton("Cancel"){ dialogInterface, id ->
                        findNavController().popBackStack()
                    }

                    show()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    private fun saveRecipeAndClose(){

        val recipe = Recipe(binding.recipeNameTxt.text.toString(), recipeStepList)

        val repository = RecipeRepository(requireActivity())
        repository.save(recipe)

        val bundle = bundleOf("recipe" to recipe)
        findNavController().navigate(R.id.action_createRecipeFragment_to_recipeDetailFragment, bundle)
    }

    private fun openEditStepDialog(recipeStep : RecipeStep?){

        val editStepPopup = EditStepPopup(requireContext(), recipeStep, recipeStepList)
        editStepPopup.show()

        editStepPopup.setOnDismissListener {

            adapter.notifyDataSetChanged()
        }
    }
}























