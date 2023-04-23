package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R

class RecipePreviewAdapter(var recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipePreviewAdapter.PreviewRecipeViewHolder>() {

    inner class PreviewRecipeViewHolder(currentItemView: View) :
        RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return PreviewRecipeViewHolder(view)
    }


    override fun onBindViewHolder(holder: PreviewRecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        val favorite: Boolean = currentRecipe.tags.contains(Tag.FAVORITE)
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtRecipe).text = currentRecipe.name

            val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)

            if (favorite) {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context, R.drawable.icon_fav_true
                    )
                )
            } else {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context, R.drawable.icon_fav_false
                    )
                )
            }

            btnFavorite.setOnClickListener() {
                if (favorite) {
                    currentRecipe.tags.remove(Tag.FAVORITE)
                } else {
                    currentRecipe.tags.add(Tag.FAVORITE)
                }

                notifyDataSetChanged()
            }
        }

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("recipe" to currentRecipe)
            it.findNavController()
                .navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        }
    }


    override fun getItemCount(): Int = recipeList.size
    fun filteredList(filteredList: MutableList<Recipe>) {
        recipeList = filteredList
        notifyDataSetChanged()
    }
}