package be.LarsVinz.MealMap.Views.Recipes.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentShoppingListBinding


class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)

        val recipeRepository = RecipePreferencesRepository(requireContext())
        val selectShoppingList = binding.autoCompleteTextView
        val shoppingListNames = arrayListOf<String>()
        val selectedRecipes = arrayListOf<Recipe>()

        selectedRecipes.addAll(recipeRepository.loadAllRecipes())

        for (item in selectedRecipes) {
            shoppingListNames.add(item.name)
        }

        adapterItems = ArrayAdapter(
            this.requireContext(), android.R.layout.simple_spinner_dropdown_item, shoppingListNames
        )
        selectShoppingList.setAdapter(adapterItems)
        selectShoppingList.setOnItemClickListener { _: AdapterView<*>, _: View, i: Int, _: Long ->
            val currentItem = selectedRecipes[i]

            binding.txtInfoEmtyIngredient.text = "This recipe does not contain any ingredients."
            if (currentItem.ingredients.isEmpty()) {
                binding.txtInfoEmtyIngredient.visibility = View.VISIBLE
            } else {
                binding.txtInfoEmtyIngredient.visibility = View.GONE
            }

            val adapter = GroceryAdapter(currentItem.ingredients)
            binding.RecyclerViewGroceries.adapter = adapter
            binding.RecyclerViewGroceries.layoutManager = LinearLayoutManager(this.context)
        }

        return binding.root
    }
}