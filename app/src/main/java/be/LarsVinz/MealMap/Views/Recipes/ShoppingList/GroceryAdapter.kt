package be.LarsVinz.MealMap.Views.Recipes.ShoppingList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.R

class GroceryAdapter(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {
    inner class GroceryViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grocery, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val currentGrocery = ingredients[position]
        holder.itemView.apply {
            val text: String = String.format("%s :  %d %s",currentGrocery.name , currentGrocery.amount, currentGrocery.unit.name) //TODO moet beter kunnen
            findViewById<CheckBox>(R.id.CheckBoxGrocery).text = text
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }


}