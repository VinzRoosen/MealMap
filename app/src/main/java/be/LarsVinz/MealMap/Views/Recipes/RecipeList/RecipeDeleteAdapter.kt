package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R

class RecipeDeleteAdapter(val recipeList: List<Recipe>, val deleteRecipeList: ArrayList<Recipe>): RecyclerView.Adapter<RecipeDeleteAdapter.RecipeDeleteViewHolder>() {
    private val CheckedRecipeList = ArrayList<Int>()
    inner class RecipeDeleteViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDeleteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_delete, parent, false)
        return RecipeDeleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDeleteViewHolder, position: Int) {
        val currentItem = recipeList[position]
        holder.itemView.apply {
            val checkBox = findViewById<CheckBox>(R.id.checkBoxDeleteRecipe)

            checkBox.text = currentItem.name
            checkBox.isChecked = CheckedRecipeList.contains(position)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    deleteRecipeList.add(currentItem)
                } else {
                    deleteRecipeList.remove(currentItem)
                }
            }
        }

    }
    override fun getItemCount(): Int = recipeList.size
}