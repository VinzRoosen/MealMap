package be.LarsVinz.MealMap.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setupMenuDrawer()

        setContentView(binding.root)
    }

    private fun setupMenuDrawer() {
        menuBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            R.string.open_menu,
            R.string.close_menu
        )
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_navToOverview -> navigateToOverview()
                R.id.menu_navToRecipeList -> navigateToRecipeList()
                R.id.menu_navToShoppingList -> navigateToShoppingList()
            }
            true
        }
    }

    private fun navigateToOverview(){
        findNavController(binding.navHostFragment.id).navigate(R.id.action_global_overviewFragment)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun navigateToRecipeList(){
        findNavController(binding.navHostFragment.id).navigate(R.id.action_global_recipeListFragment)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun navigateToShoppingList(){
        findNavController(binding.navHostFragment.id).navigate(R.id.action_global_shoppingListFragment)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // we need to do this to respond correctly to clicks on menu items, otherwise it won't be caught
        if(menuBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}