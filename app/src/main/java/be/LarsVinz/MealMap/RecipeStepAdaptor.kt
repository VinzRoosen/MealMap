package be.LarsVinz.MealMap

import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeStepAdaptor(private val items: List<RecipeStep>, val onItemClick: (RecipeStep) -> Unit) : RecyclerView.Adapter<RecipeStepAdaptor.RecipeStepViewHolder>() {

    inner class RecipeStepViewHolder(current : View) : RecyclerView.ViewHolder(current)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeStepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_step, parent, false)
        return RecipeStepViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeStepViewHolder, position: Int) {
        val currentRecipeStep = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.stepNumberTxt).text = (position + 1).toString()
            findViewById<TextView>(R.id.stepExplanationTxt).text = currentRecipeStep.explanation

            if (currentRecipeStep.timerLength == 0){
                // source: https://stackoverflow.com/questions/11169360/android-remove-button-dynamically

                val timerButton = findViewById<Button>(R.id.timerBtn)
                val layout = timerButton.parent as ViewGroup

                layout.removeView(timerButton)
            }
        }

        holder.itemView.setOnClickListener{
            onItemClick(currentRecipeStep)
        }
    }

    override fun getItemCount(): Int = items.size
}