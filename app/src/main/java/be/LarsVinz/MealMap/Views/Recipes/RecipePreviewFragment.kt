package be.LarsVinz.MealMap.Views.Recipes

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.Views.Recipes.RecipeList.RecipeTagAdapter
import be.LarsVinz.MealMap.databinding.ItemRecipePreviewBinding

class RecipePreviewFragment(val recipe : Recipe, val showFavoriteButton : Boolean) : Fragment(R.layout.item_recipe_preview) {

    lateinit var binding: ItemRecipePreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ItemRecipePreviewBinding.inflate(layoutInflater)

        // set name
        binding.txtRecipe.text = recipe.name

        // set tags
        binding.RecyclerViewTag.adapter = RecipeTagAdapter(recipe.tags)
        binding.RecyclerViewTag.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // set image
        recipe.imagePath?.let{

            val image = BitmapFactory.decodeFile(it)
            binding.imageRecipePreview.setImageBitmap(image)
        } ?: run {

            binding.imageRecipePreview.visibility = View.GONE
        }

        // set favorite button
        if (!showFavoriteButton) binding.btnFavorite.visibility = View.GONE

        // set onClick
        binding.recipePreviewBtn.setOnClickListener {
            val bundle = bundleOf("recipe" to recipe)
            it.findNavController().navigate(R.id.action_overviewFragment_to_recipeDetailFragment, bundle)
        }

        return binding.root
    }

    fun openRecipe(){

    }
}