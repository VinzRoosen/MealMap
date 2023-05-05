package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentRecipeBinding

class RecipeFragment(private val editable : Boolean, private val steps : List<RecipeStep>, private val ingredients : List<Ingredient>) : Fragment(R.layout.fragment_recipe) {

    private lateinit var binding: FragmentRecipeBinding

    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var recipeStepAdapter: RecipeStepAdaptor

    private var ingredientList = listOf<Ingredient>()
    private var recipeStepList = listOf<RecipeStep>()

    private var ingredientButtonState = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecipeBinding.inflate(layoutInflater)

        setRecipeRecycleViews()

        if (editable){
            binding.infoAddRecipeTxt.text = "Click + to add a recipe step"
            binding.infoAddIngredientTxt.text = "Click here to add an ingredient!"

            binding.addRecipeBtn.setOnClickListener  { openEditStepPopup(null) }
        }
        else{
            binding.infoAddRecipeTxt.text = "Edit this recipe to add steps!"
            binding.infoAddIngredientTxt.text = "Edit this recipe to add ingredients!"

            binding.addRecipeBtn.visibility = View.GONE
        }

        setRecipeInfotext(recipeStepList.isNotEmpty())
        setIngredientInfotext(ingredientList.isNotEmpty())

        binding.ingredientBtn.setOnClickListener { onIngredientButton() }

        return binding.root
    }

    fun onRecipeStepClicked(step: RecipeStep){

        openEditStepPopup(step)
    }

    fun onIngredientClicked(){

        openEditIngredientPopup()
    }

    fun onRecipeStepChanged(){

        recipeStepAdapter.notifyDataSetChanged()
        setRecipeInfotext(recipeStepList.isNotEmpty())
    }

    fun onIngredientChanged(){

        ingredientAdapter.notifyDataSetChanged()
        setIngredientInfotext(ingredientList.isNotEmpty())
    }

    fun setRecipeData(ingredientList : List<Ingredient>, recipeStepList : List<RecipeStep>){

        this.ingredientList = ingredientList
        this.recipeStepList = recipeStepList
    }

    fun setRecipeData(recipe: Recipe){

        setRecipeData(recipe.ingredients, recipe.steps)
    }

    private fun setRecipeInfotext(status : Boolean){

        if (status) binding.infoAddRecipeTxt.visibility = View.GONE
        else        binding.infoAddRecipeTxt.visibility = View.VISIBLE
    }

    private fun setIngredientInfotext(status : Boolean){

        if (status) binding.infoAddIngredientTxt.visibility = View.GONE
        else        binding.infoAddIngredientTxt.visibility = View.VISIBLE
    }

    private fun setRecipeRecycleViews(){

        ingredientAdapter = IngredientAdapter(ingredientList)
        binding.ingredientRvw.adapter = ingredientAdapter
        binding.ingredientRvw.layoutManager = GridLayoutManager(this.context, 2)
        binding.ingredientRvw.setOnTouchListener { view, motionEvent -> // TODO: Dit moet omdat de setOnClickListener niet werkt, maar miss beter anders?
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                onIngredientClicked()
                view.performClick()
            }
            false;
        }

        recipeStepAdapter = RecipeStepAdaptor(recipeStepList, requireContext()) { recipeStep ->
            onRecipeStepClicked(recipeStep)
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
            binding.infoAddIngredientTxt.visibility = View.GONE
        }
        else{
            ingredientButtonState = true
            binding.ingredientBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_arrow_downward, 0, 0, 0)
            binding.ingredientRvw.visibility = View.VISIBLE
            binding.ingredientSeperator.visibility = View.VISIBLE
        }
    }

    private fun openEditStepPopup(recipeStep : RecipeStep?){

        if (!editable) return

        EditRecipeStepPopup(requireContext(), recipeStep, steps as MutableList<RecipeStep>).apply {

            setOnDismissListener { onRecipeStepChanged() }
            show()
        }
    }

    private fun openEditIngredientPopup(){

        if (!editable) return

        EditRecipeIngredientPopup(requireContext(), ingredients as MutableList<Ingredient>).apply {
            setOnDismissListener { onIngredientChanged() }
            show()
        }
    }
}