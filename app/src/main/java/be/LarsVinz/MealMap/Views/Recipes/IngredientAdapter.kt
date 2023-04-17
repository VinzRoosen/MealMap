package be.LarsVinz.MealMap.Views.Recipes

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.R


class IngredientAdapter(val items : List<Ingredient>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(current : View) : RecyclerView.ViewHolder(current)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngredient = items[position]
        holder.itemView.apply {

            findViewById<TextView>(R.id.ingredientNameTxt).text = currentIngredient.ingredientName
            findViewById<TextView>(R.id.ingredientAmountTxt).text = "${currentIngredient.amount} ${currentIngredient.unit}"
        }
    }

    override fun getItemCount(): Int = items.size
}