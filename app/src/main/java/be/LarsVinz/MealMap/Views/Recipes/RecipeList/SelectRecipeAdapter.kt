package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R

class SelectRecipeAdapter(
    var recipeList: List<Recipe>, private val selectedRecipes: ArrayList<Recipe>
) : RecyclerView.Adapter<SelectRecipeAdapter.RecipeDeleteViewHolder>() {
    private val CheckedRecipeList = ArrayList<Int>()

    inner class RecipeDeleteViewHolder(currentItemView: View) :
        RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDeleteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_delete, parent, false)
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
                    selectedRecipes.add(currentItem)
                } else {
                    selectedRecipes.remove(currentItem)
                }
            }
        }
    }

    fun filteredList(filteredList: List<Recipe>) {
        recipeList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = recipeList.size
}