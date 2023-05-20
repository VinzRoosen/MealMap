package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R

class RecipePreviewAdapter(var recipeList: List<Recipe>) : RecyclerView.Adapter<RecipePreviewAdapter.PreviewRecipeViewHolder>() {

    inner class PreviewRecipeViewHolder(itemView: View, var isFavorite : Boolean) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewRecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return PreviewRecipeViewHolder(itemView, false)
    }

    override fun onBindViewHolder(holder: PreviewRecipeViewHolder, position: Int) {

        val currentRecipe = recipeList[position]

        holder.itemView.apply {

            holder.isFavorite = currentRecipe.tags.contains(Tag.FAVORITE)

            val btnFavorite = findViewById<ImageButton>(R.id.btnFavorite)
            val recyclerViewTag = findViewById<RecyclerView>(R.id.RecyclerViewTag)

            currentRecipe.imagePath?.let{

                val image = BitmapFactory.decodeFile(it)
                findViewById<ImageView>(R.id.imageRecipePreview).setImageBitmap(image)
            } ?: run {

                findViewById<ImageView>(R.id.imageRecipePreview).visibility = View.GONE
            }

            findViewById<TextView>(R.id.txtRecipe).text = currentRecipe.name

            val tagsToShow = mutableListOf<Tag>().apply {
                addAll(currentRecipe.tags)
                remove(Tag.FAVORITE)
            }
            val adapter = RecipeTagAdapter(tagsToShow)
            recyclerViewTag.adapter = adapter
            recyclerViewTag.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            // set correct image
            var image = R.drawable.icon_fav_false
            if (holder.isFavorite) image = R.drawable.icon_fav_true
            btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, image))

            btnFavorite.setOnClickListener {

                holder.isFavorite = !holder.isFavorite

                // set correct image
                var image = R.drawable.icon_fav_false
                if (holder.isFavorite) image = R.drawable.icon_fav_true
                btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, image))

                // create new tag list
                val newTagList = mutableListOf<Tag>().apply {

                    addAll(currentRecipe.tags)

                    if (holder.isFavorite) add(0, Tag.FAVORITE)
                    else                   remove(Tag.FAVORITE)
                }

                RecipePreferencesRepository(context).save(
                    Recipe(currentRecipe.name, currentRecipe.steps, currentRecipe.ingredients, newTagList, currentRecipe.imagePath)
                )
            }
        }

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("recipe" to currentRecipe)
            it.findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        }
    }

    override fun getItemCount() = recipeList.size

    fun filteredList(filteredList: List<Recipe>) {
        recipeList = filteredList
        notifyDataSetChanged()
    }
}
