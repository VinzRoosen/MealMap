package be.LarsVinz.MealMap.Views.Recipes.SelectRecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.RecipeList.RecipeTagAdapter
import be.LarsVinz.MealMap.databinding.ItemRecipeDeleteBinding

class SelectRecipeAdapter(
    var recipes: List<Recipe>,
    private val selectedRecipes: MutableList<Recipe>
) : RecyclerView.Adapter<SelectRecipeAdapter.RecipeDeleteViewHolder>() {

    inner class RecipeDeleteViewHolder(currentItemView: View) :
        RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDeleteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_delete, parent, false)
        return RecipeDeleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDeleteViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        val binding = ItemRecipeDeleteBinding.bind(holder.itemView)

        binding.apply {

            val tagList: List<Tag> = currentRecipe.tags

            checkBoxDeleteRecipe.text = currentRecipe.name
            checkBoxDeleteRecipe.isChecked = selectedRecipes.contains(currentRecipe)

            checkBoxDeleteRecipe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedRecipes.add(currentRecipe)
                } else {
                    selectedRecipes.remove(currentRecipe)
                }
            }

            val adapter = RecipeTagAdapter(tagList)
            RecyclerViewSelectTag.adapter = adapter
            RecyclerViewSelectTag.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun filteredList(filteredList: List<Recipe>) {
        recipes = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = recipes.size
}
