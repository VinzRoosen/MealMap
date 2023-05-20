package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentEditTagsBinding

class EditTagsFragment(val selectedtagList : MutableList<Tag>) : Fragment(R.layout.fragment_edit_tags) {

    lateinit var binding: FragmentEditTagsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditTagsBinding.inflate(layoutInflater)

        val tagList = Tag.values().toMutableList()
        tagList.remove(Tag.FAVORITE)

        binding.TagRvw.adapter = TagAdapter(tagList, selectedtagList)
        binding.TagRvw.layoutManager = GridLayoutManager(this.context, 2)

        return binding.root
    }
}