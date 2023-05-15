package be.LarsVinz.MealMap.Views.ShoppingList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.ItemGroceryBinding

class GroceryAdapter(private val ingredients: List<Ingredient>, private val selectedGroceries: MutableList<Ingredient>) :
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

            checkBoxGrocery.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedGroceries.add(currentGrocery)
                } else {
                    selectedGroceries.remove(currentGrocery)
                }
            }

            txtGroceryAmountUnit.text = "${currentGrocery.amount}\t${currentGrocery.unit}"
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

}