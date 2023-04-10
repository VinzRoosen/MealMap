package be.LarsVinz.MealMap.Views.Recipes

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.R


class RecipeStepAdaptor(private val items: List<RecipeStep>, val context :  Context, val onItemClick: (RecipeStep) -> Unit) : RecyclerView.Adapter<RecipeStepAdaptor.RecipeStepViewHolder>() {

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

            val timerBtn = findViewById<Button>(R.id.timerBtn)

            timerBtn.setOnClickListener{
                startTimer("Test Timer", currentRecipeStep.timerLength)
            }

            if (currentRecipeStep.timerLength == 0){

                timerBtn.visibility = View.GONE
            }
            else {
                timerBtn.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnClickListener{
            onItemClick.invoke(currentRecipeStep)
        }
    }

    fun startTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(context, intent, null) // TODO: ask for permission
    }

    override fun getItemCount(): Int = items.size
}