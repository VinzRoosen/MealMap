package be.LarsVinz.MealMap.Views.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentShoppingListBinding


class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {
    private lateinit var binding : FragmentShoppingListBinding
    private lateinit var shoppingListRepository : ShoppingListRepository
    private lateinit var selectedGroceries : MutableList<Ingredient>
    private lateinit var adapter : GroceryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)

        shoppingListRepository = ShoppingListRepository(requireContext())
        val shoppingList = shoppingListRepository.loadAllIngredients()
        selectedGroceries = mutableListOf()

        adapter = GroceryAdapter(shoppingList, selectedGroceries)
        binding.recyclerViewGroceries.adapter = adapter
        binding.recyclerViewGroceries.layoutManager = LinearLayoutManager(requireContext())

        val txtShoppingListEmpty = binding.txtShoppingListEmpty
        txtShoppingListEmpty.text = "no groceries added yet, click on + to add a recipe"
        txtShoppingListEmpty.visibility = if (shoppingList.isEmpty()) View.VISIBLE else View.INVISIBLE

        binding.btnNewShoppingList.setOnClickListener { newShoppingList() }
        binding.btnRemoveRecipeFromShoppingList.setOnClickListener { removeRecipeFromShoppingList() }

        return binding.root
    }

    private fun removeRecipeFromShoppingList() {
        shoppingListRepository.deleteIngredients(selectedGroceries)
        selectedGroceries.clear()
        adapter.notifyDataSetChanged()
    }

    private fun newShoppingList() {
        AddShoppingListPopup(requireContext(), requireView()).show()
        adapter.notifyDataSetChanged()
    }
}
