package be.LarsVinz.MealMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class RecipeDeleteAdapter(val items: List<Recipe>, function: () -> Unit) : RecyclerView.Adapter<RecipeDeleteAdapter.RecipeDeleteViewHolder>() {
    private val gecontroleerdeItems = ArrayList<Int>()
    private val deleteRecipeFragment = DeleteRecipeFragment();
    inner class RecipeDeleteViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView){
        private val checkBoxDelete: CheckBox = itemView.findViewById(R.id.checkBoxDeleteRecipe)
        init {
            checkBoxDelete.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    gecontroleerdeItems.add(adapterPosition)
                } else {
                    gecontroleerdeItems.remove(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDeleteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_delete, parent, false)
        return RecipeDeleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDeleteViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.apply {
            val checkBox = findViewById<CheckBox>(R.id.checkBoxDeleteRecipe)
            checkBox.text = currentItem.recipeName
            checkBox.isChecked = gecontroleerdeItems.contains(position)
        }
    }

    override fun getItemCount(): Int = items.size
}