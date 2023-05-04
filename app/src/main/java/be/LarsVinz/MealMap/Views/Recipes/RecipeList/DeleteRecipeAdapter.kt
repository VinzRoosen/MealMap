package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R

class DeleteRecipeAdapter(
    var recipeList: List<Recipe>, private val selectedRecipes: ArrayList<Recipe>
) : RecyclerView.Adapter<DeleteRecipeAdapter.RecipeDeleteViewHolder>() {
    private val CheckedRecipeList = ArrayList<Int>()

    inner class RecipeDeleteViewHolder(currentItemView: View) :
        RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDeleteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_delete, parent, false)
        return RecipeDeleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDeleteViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.itemView.apply {
            val checkBox = findViewById<CheckBox>(R.id.checkBoxDeleteRecipe)
            val recyclerViewSelectTag = findViewById<RecyclerView>(R.id.RecyclerViewSelectTag)
            val tagList: List<Tag> = currentRecipe.tags

            checkBox.text = currentRecipe.name
            checkBox.isChecked = CheckedRecipeList.contains(position)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedRecipes.add(currentRecipe)
                } else {
                    selectedRecipes.remove(currentRecipe)
                }
            }

            val adapter = RecipeTagAdapter(tagList)
            recyclerViewSelectTag.adapter = adapter
            recyclerViewSelectTag.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun filteredList(filteredList: List<Recipe>) {
        recipeList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = recipeList.size
}