package be.LarsVinz.OurApp

import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeStepAdaptor(val items: List<RecipeStep>) : RecyclerView.Adapter<RecipeStepAdaptor.RecipeStepViewHolder>() {

    inner class RecipeStepViewHolder(current : View) : RecyclerView.ViewHolder(current)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeStepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_step, parent, false)
        return RecipeStepViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeStepViewHolder, position: Int) {
        val currentTodoItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.stepNumberTxt).text = (position + 1).toString()
            findViewById<EditText>(R.id.stepExplanationTxt).hint = "Write the step here"
        }
    }

    override fun getItemCount(): Int = items.size
}