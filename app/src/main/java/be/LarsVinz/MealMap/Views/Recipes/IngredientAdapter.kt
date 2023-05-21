package be.LarsVinz.MealMap.Views.Recipes

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.RecipeUnit
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.R


class IngredientAdapter(val items : List<Ingredient>, val multiplier : Int) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(current : View) : RecyclerView.ViewHolder(current)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngredient = items[position]
        holder.itemView.apply {

            findViewById<TextView>(R.id.ingredientNameTxt).text = currentIngredient.name

            var amount = "${currentIngredient.amount * multiplier}"
            var unit = currentIngredient.unit.toString().lowercase()

            if (currentIngredient.amount == 0.0) amount = ""
            if (currentIngredient.unit == RecipeUnit.LEEG) unit = ""

            findViewById<TextView>(R.id.ingredientAmountTxt).text = "$amount $unit"
        }
    }

    override fun getItemCount(): Int = items.size
}