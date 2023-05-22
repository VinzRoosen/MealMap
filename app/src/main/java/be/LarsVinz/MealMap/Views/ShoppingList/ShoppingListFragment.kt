package be.LarsVinz.MealMap.Views.ShoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        
        populatePersonAmountSpinner()

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
    private fun setAdapter(multiplier : Int){
        adapter = GroceryAdapter(shoppingList, selectedGroceries, multiplier)
        binding.recyclerViewGroceries.adapter = adapter
        binding.recyclerViewGroceries.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun populatePersonAmountSpinner(){

        val adapterItems = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf(1, 2, 3, 4, 5, 6, 7, 8))
        binding.spnrAmountOfPeople.adapter = adapterItems
        binding.spnrAmountOfPeople.setSelection(0)
        binding.spnrAmountOfPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setAdapter(position+1)
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
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
