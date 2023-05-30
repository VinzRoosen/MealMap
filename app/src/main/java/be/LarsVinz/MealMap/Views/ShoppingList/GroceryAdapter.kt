package be.LarsVinz.MealMap.Views.ShoppingList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.RecipeUnit
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.ItemGroceryBinding

class GroceryAdapter(private val ingredients: List<Ingredient>, private val selectedGroceries: MutableList<Ingredient>, private val multiplier: Int) :
    RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {
    inner class GroceryViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grocery, parent, false)

        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val currentGrocery = ingredients[position]
        val binding = ItemGroceryBinding.bind(holder.itemView)

        binding.apply {

            checkBoxGrocery.text = currentGrocery.name
            checkBoxGrocery.isChecked = selectedGroceries.contains(currentGrocery)

            var amount = "${currentGrocery.amount * multiplier}"
            var unit = currentGrocery.unit.toString().lowercase()

            if (currentGrocery.amount == 0.0) amount = ""
            if (currentGrocery.unit == RecipeUnit.LEEG) unit = ""

            checkBoxGrocery.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedGroceries.add(currentGrocery)
                } else {
                    selectedGroceries.remove(currentGrocery)
                }
            }

            txtGroceryAmountUnit.text = "$amount $unit"
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

}