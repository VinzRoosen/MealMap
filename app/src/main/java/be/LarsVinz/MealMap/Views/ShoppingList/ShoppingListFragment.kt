package be.LarsVinz.MealMap.Views.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentShoppingListBinding

class ShoppingListFragment : Fragment() {
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var adapter: GroceryAdapter

    private var shoppingList = mutableListOf<Ingredient>()
    private val selectedGroceries = mutableListOf<Ingredient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        shoppingListRepository = ShoppingListRepository(requireContext())
        shoppingList = shoppingListRepository.loadAll() as MutableList<Ingredient>

        adapter = GroceryAdapter(shoppingList, selectedGroceries)
        binding.recyclerViewGroceries.adapter = adapter
        binding.recyclerViewGroceries.layoutManager = LinearLayoutManager(requireContext())

        val txtShoppingListEmpty = binding.txtShoppingListEmpty
        txtShoppingListEmpty.text = getString(R.string.emptyGrocery)
        txtShoppingListEmpty.visibility =
            if (shoppingList.isEmpty()) View.VISIBLE else View.INVISIBLE

        val btnRemoveRecipeFromShoppingList = binding.btnRemoveRecipeFromShoppingList
        binding.btnNewShoppingList.setOnClickListener { newShoppingList() }
        btnRemoveRecipeFromShoppingList.setOnClickListener {
            removeRecipeFromShoppingList(
                selectedGroceries
            )
        }

        if (shoppingList.isNotEmpty()) {
            btnRemoveRecipeFromShoppingList.setOnLongClickListener {
                removeRecipeFromShoppingList(shoppingList)
                Toast.makeText(requireContext(), "All recipes are deleted", Toast.LENGTH_SHORT)
                    .show()
                true
            }
        }

        return binding.root
    }

    private fun removeRecipeFromShoppingList(toBeDeleted: MutableList<Ingredient>) {
        shoppingListRepository.deleteAll(toBeDeleted)
        shoppingList.removeAll(toBeDeleted)
        toBeDeleted.clear()

        adapter.notifyDataSetChanged()
    }

    private fun newShoppingList() {
        AddShoppingListPopup(requireContext(), shoppingList, binding.root).apply {
            setOnDismissListener {
                shoppingListRepository.saveAll(shoppingList)
                adapter.notifyDataSetChanged()
            }
            show()
        }
    }
}
