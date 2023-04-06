package be.LarsVinz.MealMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class RecipeDeleteAdapter(val items: List<Recipe>, function: () -> Unit) : RecyclerView.Adapter<RecipeDeleteAdapter.RecipeDeleteViewHolder>() {
    inner class RecipeDeleteViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDeleteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_delete, parent, false)
        return RecipeDeleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDeleteViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.apply {
            findViewById<CheckBox>(R.id.checkBoxDeleteRecipe).text = currentItem.recipeName;
        }
    }

    override fun getItemCount(): Int = items.size
}