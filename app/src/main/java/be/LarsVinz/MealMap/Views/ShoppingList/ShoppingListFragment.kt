package be.LarsVinz.MealMap.Views.Recipes.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.RecipePreferencesRepository
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentShoppingListBinding


class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {
    private lateinit var binding : FragmentShoppingListBinding
    private lateinit var adapterItems : ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)

        val shoppingListRepository = ShoppingListRepository(requireContext())
        val shoppingList = shoppingListRepository.loadAllIngredients()

        val adapter = GroceryAdapter(shoppingList)
        binding.RecyclerViewGroceries.adapter = adapter
        binding.RecyclerViewGroceries.layoutManager = LinearLayoutManager(this.context)

        binding.btnNewShoppingList.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("case", "select_shopping_list")
            findNavController().navigate(R.id.action_shoppingListFragment_to_selectRecipeFragment, bundle)
        }


        return binding.root
    }
}