package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipeRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.ItemRecipePreviewBinding

class RecipePreviewAdapter(var recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipePreviewAdapter.PreviewRecipeViewHolder>() {

    inner class PreviewRecipeViewHolder(
        val binding: ItemRecipePreviewBinding,
        var isFavorite: Boolean
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewRecipeViewHolder {

        val binding =
            ItemRecipePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PreviewRecipeViewHolder(binding, false)
    }

    override fun onBindViewHolder(holder: PreviewRecipeViewHolder, position: Int) {

        val currentRecipe = recipeList[position]

        holder.apply {

            isFavorite = currentRecipe.tags.contains(Tag.FAVORITE)

            currentRecipe.imagePath?.let {

                val image = BitmapFactory.decodeFile(it)
                binding.imageRecipePreview.setImageBitmap(image)
            } ?: run {

                binding.imageRecipePreview.visibility = View.GONE
            }

            binding.txtRecipe.text = currentRecipe.name

            val tagsToShow = mutableListOf<Tag>().apply {
                addAll(currentRecipe.tags)
                remove(Tag.FAVORITE)
            }

            val adapter = RecipeTagAdapter(tagsToShow)
            binding.RecyclerViewTag.adapter = adapter
            binding.RecyclerViewTag.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            // set favorite image
            var image = R.drawable.icon_fav_false
            if (isFavorite) image = R.drawable.icon_fav_true
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, image))

            binding.btnFavorite.setOnClickListener {

                isFavorite = !isFavorite

                // set correct image
                var image = R.drawable.icon_fav_false
                if (isFavorite) image = R.drawable.icon_fav_true
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        image
                    )
                )

                // create new tag list
                val newTagList = mutableListOf<Tag>().apply {

                    addAll(currentRecipe.tags)

                    if (isFavorite) add(0, Tag.FAVORITE)
                    else remove(Tag.FAVORITE)
                }

                RecipeRepository(itemView.context).save(
                    Recipe(
                        currentRecipe.name,
                        currentRecipe.steps,
                        currentRecipe.ingredients,
                        newTagList,
                        currentRecipe.imagePath
                    )
                )
            }
            binding.recipePreviewBtn.setOnClickListener {
                val bundle = bundleOf("recipe" to currentRecipe)
                it.findNavController()
                    .navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
            }
        }
    }

    override fun getItemCount() = recipeList.size

    fun filteredList(filteredList: List<Recipe>) {
        recipeList = filteredList
        notifyDataSetChanged()
    }
}