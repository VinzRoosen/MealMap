package be.LarsVinz.MealMap.Views.Recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentRecipeBinding

class RecipeFragment() : Fragment(R.layout.fragment_recipe) {

    private lateinit var binding: FragmentRecipeBinding

    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var recipeStepAdapter: RecipeStepAdaptor

    private var ingredientList = listOf<Ingredient>()
    private var recipeStepList = listOf<RecipeStep>()

    private var ingredientButtonState = true

    private var onRecipeStepItemClick: (RecipeStep) -> Unit = {}
    private var onIngredientItemClick: (Ingredient) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRecipeBinding.inflate(layoutInflater)

        setRecipeRecycleViews()

        binding.ingredientBtn.setOnClickListener { onIngredientButton() }

        return binding.root
    }

    fun setOnRecipeStepClicked(event : (RecipeStep) -> Unit){
        onRecipeStepItemClick = event
    }

    fun setOnIngredientClicked(event : (Ingredient) -> Unit){
        onIngredientItemClick = event
    }

    fun onRecipeStepChanged(){
        recipeStepAdapter.notifyDataSetChanged()
    }

    fun onIngredientChanged(){
        ingredientAdapter.notifyDataSetChanged()
    }

    fun setRecipeData(ingredientList : List<Ingredient>, recipeStepList : List<RecipeStep>){

        this.ingredientList = ingredientList
        this.recipeStepList = recipeStepList

        this.ingredientList = listOf(
            Ingredient("Appels", 5, "kg"),
            Ingredient("Peren", 5, "kg"),
            Ingredient("Appelsienensdqsdqsdqsdqsdqsdqsdqdqsdqsdqsdqdqsdqsdqd", 5, "kg"),
            Ingredient("beren", 5, "kg")
        )
    }

    fun setRecipeData(recipe: Recipe){

        setRecipeData(recipe.ingredients, recipe.steps)
    }

    private fun setRecipeRecycleViews(){

        ingredientAdapter = IngredientAdapter(ingredientList)
        binding.ingredientRvw.adapter = ingredientAdapter
        binding.ingredientRvw.layoutManager = GridLayoutManager(this.context, 2)

        recipeStepAdapter = RecipeStepAdaptor(recipeStepList, requireContext()) { recipeStep ->

            onRecipeStepItemClick.invoke(recipeStep)
        }

        binding.recipeStepRvw.adapter = recipeStepAdapter
        binding.recipeStepRvw.layoutManager = LinearLayoutManager(this.context)
    }

    private fun onIngredientButton(){

        if (ingredientButtonState == true){
            ingredientButtonState = false
            binding.ingredientBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_arrow_forward, 0, 0, 0)
            binding.ingredientRvw.visibility = View.GONE
            binding.ingredientSeperator.visibility = View.GONE
        }
        else{
            ingredientButtonState = true
            binding.ingredientBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_arrow_downward, 0, 0, 0)
            binding.ingredientRvw.visibility = View.VISIBLE
            binding.ingredientSeperator.visibility = View.VISIBLE
        }
    }
}