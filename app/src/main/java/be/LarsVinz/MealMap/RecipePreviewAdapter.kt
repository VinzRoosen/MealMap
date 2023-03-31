package be.LarsVinz.MealMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class RecipePreviewAdapter (val items: List<Recipe>): RecyclerView.Adapter<RecipePreviewAdapter.testViewHolder>(){
    inner class testViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): testViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return testViewHolder(view)
    }

    override fun onBindViewHolder(holder: testViewHolder, position: Int) {
        val currentRecipe = items[position]
        holder.itemView.apply {

            findViewById<TextView>(R.id.txtRecipe).text = currentRecipe.recipeName
        }

        holder.itemView.setOnClickListener{

            val bundle = bundleOf("recipe" to currentRecipe)
            it.findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = items.size
}