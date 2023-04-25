package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R

class RecipePreviewAdapter(var recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipePreviewAdapter.PreviewRecipeViewHolder>() {
    var favorite = false //currentRecipe.tags.contains(Tag.FAVORITE)
    inner class PreviewRecipeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewRecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return PreviewRecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PreviewRecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]

        holder.itemView.apply {
            val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)
            val recyclerViewTag = findViewById<RecyclerView>(R.id.RecyclerViewTag)
            val tagList: List<Tag> = listOf(Tag.BBQ, Tag.PASTA, Tag.BREAKFAST, Tag.GLUTEN_FREE)

            findViewById<ImageView>(R.id.imageRecipePreview).setImageResource(R.drawable.icon_test_recipe_preview)
            findViewById<TextView>(R.id.txtRecipe).text = currentRecipe.name

            val adapter = RecipeTagAdapter(tagList)
            recyclerViewTag.adapter = adapter
            recyclerViewTag.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, if (favorite) R.drawable.icon_fav_true else R.drawable.icon_fav_false))
            btnFavorite.setOnClickListener {
                favorite = !favorite
                /*
                if (newFavorite) {
                    currentRecipe.tags.add(Tag.FAVORITE)
                } else {
                    currentRecipe.tags.remove(Tag.FAVORITE)
                }
                 */
                notifyDataSetChanged()
            }
        }

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("recipe" to currentRecipe)
            it.findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        }
    }

    override fun getItemCount() = recipeList.size

    fun filteredList(filteredList: MutableList<Recipe>) {
        recipeList = filteredList
        notifyDataSetChanged()
    }
}
