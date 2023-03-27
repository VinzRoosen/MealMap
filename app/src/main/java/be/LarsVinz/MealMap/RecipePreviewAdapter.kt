package be.LarsVinz.MealMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipePreviewAdapter (val items: MutableList<Recipe>): RecyclerView.Adapter<RecipePreviewAdapter.testViewHolder>(){
    inner class testViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): testViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return testViewHolder(view)
    }

    override fun onBindViewHolder(holder: testViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtRecipe).text = currentItem.recipeName
        }
    }

    override fun getItemCount(): Int = items.size
}