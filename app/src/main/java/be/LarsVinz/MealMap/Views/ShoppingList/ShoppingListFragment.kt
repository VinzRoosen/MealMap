package be.LarsVinz.MealMap.Views.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.LarsVinz.MealMap.Models.ShoppingListRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.FragmentShoppingListBinding


class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {
    private lateinit var binding: FragmentShoppingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)

        val shoppingListRepository = ShoppingListRepository(requireContext())
        val shoppingList = shoppingListRepository.loadAllIngredients()

        val adapter = GroceryAdapter(shoppingList)
        binding.RecyclerViewGroceries.adapter = adapter
        binding.RecyclerViewGroceries.layoutManager = LinearLayoutManager(this.context)

        setTextViewEmptyShoppingList()

        binding.btnNewShoppingList.setOnClickListener { newShoppingList() }

        binding.btnRemoveRecipeFromShoppingList.setOnClickListener{ removeRecipeFromShoppingList()}

        return binding.root
    }

    private fun setTextViewEmptyShoppingList() {
       binding.txtShoppingListEmpty.text = "no groceries added yet, click on + to add a recipe"
    }

    private fun removeRecipeFromShoppingList() {
        val bundle = Bundle()
        bundle.putString("case", "delete_shopping_list")
        findNavController().navigate(
            R.id.action_shoppingListFragment_to_selectRecipeFragment,
            bundle
        )
    }

    private fun newShoppingList() {
        val bundle = Bundle()
        bundle.putString("case", "select_shopping_list")
        findNavController().navigate(
            R.id.action_shoppingListFragment_to_selectRecipeFragment,
            bundle
        )
    }
}