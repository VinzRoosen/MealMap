package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R

class RecipePreviewAdapter (val items: List<Recipe>): RecyclerView.Adapter<RecipePreviewAdapter.PreviewRecipeViewHolder>(){
    inner class PreviewRecipeViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return PreviewRecipeViewHolder(view)
    }
    override fun onBindViewHolder(holder: PreviewRecipeViewHolder, position: Int) {
        val currentRecipe = items[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.txtRecipe).text = currentRecipe.name
        }

        holder.itemView.setOnClickListener{
            val bundle = bundleOf("recipe" to currentRecipe)
            it.findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        }
    }
    override fun getItemCount(): Int = items.size
}