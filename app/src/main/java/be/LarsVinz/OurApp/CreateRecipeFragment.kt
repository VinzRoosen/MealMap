package be.LarsVinz.OurApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        adapter = RecipeStepAdaptor(recipeStepList)
        binding.recipeStepRvw.adapter = adapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)

        binding.addRecipeBtn.setOnClickListener {onAddRecipeBtn()}
        binding.saveRecipeBtn.setOnClickListener {onSaveRecipeBtn()}

        return binding.root
    }

    private fun onAddRecipeBtn(){
        recipeStepList.add(RecipeStep("", 0))
        adapter.notifyItemInserted(recipeStepList.size - 1)
    }

    private fun onSaveRecipeBtn(){
        throw UnsupportedOperationException()
    }
}