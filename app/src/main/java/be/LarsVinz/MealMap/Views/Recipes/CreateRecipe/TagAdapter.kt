package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.R

class TagAdapter(val items : List<Tag>, val selectedTagList : MutableList<Tag>) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    inner class TagViewHolder(current : View) : RecyclerView.ViewHolder(current)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val currentTag = items[position]
        holder.itemView.apply {
            val checkBox = findViewById<CheckBox>(R.id.checkBox)
            checkBox.text = currentTag.name.lowercase()
            checkBox.isChecked = selectedTagList.contains(currentTag)

            checkBox.setOnCheckedChangeListener { compoundButton, selected ->

                if (selected) selectedTagList.add(currentTag)
                else selectedTagList.remove(currentTag)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}