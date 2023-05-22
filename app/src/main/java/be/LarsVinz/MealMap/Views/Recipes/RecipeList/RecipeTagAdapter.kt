package be.LarsVinz.MealMap.Views.Recipes.RecipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.R

class RecipeTagAdapter(var tagList: List<Tag>) :
    RecyclerView.Adapter<RecipeTagAdapter.RecipeTagViewHolder>() {
    inner class RecipeTagViewHolder(currentItemView: View) :
        RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeTagAdapter.RecipeTagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return RecipeTagViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeTagAdapter.RecipeTagViewHolder, position: Int) {
        val currentTag = tagList[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.tagName).text = currentTag.name.lowercase()
        }
    }

    override fun getItemCount(): Int {
        return tagList.size
    }
}