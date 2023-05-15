package be.LarsVinz.MealMap.Views.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.ShoppingListRepository
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
        txtShoppingListEmpty.text = "no groceries added yet, click on + to add a recipe"
        txtShoppingListEmpty.visibility =
            if (shoppingList.isEmpty()) View.VISIBLE else View.INVISIBLE

        binding.btnNewShoppingList.setOnClickListener { newShoppingList() }
        binding.btnRemoveRecipeFromShoppingList.setOnClickListener { removeRecipeFromShoppingList() }

        return binding.root
    }

    private fun removeRecipeFromShoppingList() {
        shoppingListRepository.deleteAll(selectedGroceries)
        shoppingList.removeAll(selectedGroceries)
        selectedGroceries.clear()
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
