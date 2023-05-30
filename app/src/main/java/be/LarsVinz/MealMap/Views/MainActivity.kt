package be.LarsVinz.MealMap.Views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import be.LarsVinz.MealMap.Enums.RecipeUnit
import be.LarsVinz.MealMap.Enums.Tag
import be.LarsVinz.MealMap.Models.DataClasses.Ingredient
import be.LarsVinz.MealMap.Models.DataClasses.Recipe
import be.LarsVinz.MealMap.Models.DataClasses.RecipeStep
import be.LarsVinz.MealMap.Models.RecipeRepository
import be.LarsVinz.MealMap.R
import be.LarsVinz.MealMap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        // load day theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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
                R.id.menu_addDebugRecipes -> addDebugRecipes(applicationContext)
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

    private fun addDebugRecipes(context : Context){
        val recipeList = listOf(
            Recipe(
                "Chocoladeganache taart",
                listOf(
                    RecipeStep("Boter een springvorm of een taartring in. Bedek de randen en de bodem met een laagje boterpapier.", 0),
                    RecipeStep("Breek de witte chocolade in stukken en laat de chocolade au bain-marie smelten.", 0),
                    RecipeStep("Verkruimel de ijshoorntjes in een kom en giet er de gesmolten chocolade bij. Meng alles goed onder elkaar en stort het beslag in de taartvorm.", 0),
                    RecipeStep("Duw de bodem goed aan zodat deze overal gelijk is. Laat de taartbodem opstijven in de koelkast.", 0),
                    RecipeStep("Snijd intussen de donkere chocolade in stukjes en doe ze samen met de boter en de chocopasta in een kom.", 0),
                    RecipeStep("Breng vervolgens de room in een steelpan aan de kook. Giet de warme room op de chocolade, de chocopasta en de boter. Meng alles goed onder elkaar tot de chocolade en de boter helemaal gesmolten zijn.", 0),
                    RecipeStep("Voeg er nog een snuif grof zout aan toe en stort het beslag in de opgesteven taartvorm.", 0),
                    RecipeStep("Strooi de Brésiliennenootjes over de taart. Laat het geheel minstens 2 uur opstijven in de koelkast.", 7200),
                    RecipeStep("Snijd de taart in dunne spiesjes en serveer ze met mokka-ijs of een smaak naar keuze.", 0),
                ),
                listOf(
                    Ingredient("witte chocolade", 50.0, RecipeUnit.G),
                    Ingredient("ijshoorntjes", 25.0, RecipeUnit.G),
                    Ingredient("donkere chocolade", 100.0, RecipeUnit.G),
                    Ingredient("boter", 30.0, RecipeUnit.G),
                    Ingredient("chocopasta", 0.75, RecipeUnit.EETLEPELS),
                    Ingredient("room", 87.5, RecipeUnit.ML),
                    Ingredient("grof zout", 0.25, RecipeUnit.SNUIFJES),
                    Ingredient("bresiliennenootjes", 25.0, RecipeUnit.G),
                    Ingredient("mokka-ijs", 0.0, RecipeUnit.LEEG),
                ),
                listOf( Tag.DESSERT,Tag.SNACK),
                null
            ),
            Recipe(
                "Vol-au-vent",
                listOf(
                    RecipeStep("Bereid een ketel met een (variant op de) klassieke groentebouillon. Je kan ook zelf groentebouillon maken.", 0),
                    RecipeStep("Breng een ruime hoeveelheid water aan de kook. Spoel alle groenten, snij ze grof en doe ze in de grote pot met water.", 0),
                    RecipeStep("Voeg de verse kruiden, wat zout en de specerijen toe.", 0),
                    RecipeStep("Laat de bouillon kort sudderen en leg er vervolgens de kip in.", 0),
                    RecipeStep("Laat de kippenbouillon een uurtje koken. Gebruik een schuimspaan om tussendoor het vet en onzuiverheden weg te scheppen.", 3600),
                    RecipeStep("Schep de gare kip uit de pot en laat ze afkoelen.", 0),
                    RecipeStep("Zeef de bouillon. Een deel ervan heb je straks nodig voor het garen van de gehaktballetjes en de bereiding van de saus.", 0),
                    RecipeStep("Maak zelf de bladerdeegkoekjes om de vulling in te scheppen.", 0),
                    RecipeStep("Klop een dooier los met een klein beetje water.", 0),
                    RecipeStep("Verwarm de oven voor tot 180°C.", 0),
                    RecipeStep("Steek per gebakje een zestal ringen uit het vel bladerdeeg. Gebruik hiervoor een grote dresseerring. Steek uit vier van elke zes lapjes een rondje met behulp van een kleinere dresseerring. Bouw elk kuipje op: onderaan een ronde lap, daarboven vier 'ringen' van bladerdeeg, en bovenaan opnieuw een ronde lap. Lijm alle onderdelen aan mekaar met een laagje losgeklopte dooier. Strijk ook eigeel over de bovenkant van het gebakje, zodat het glanzend uit de oven komt. Gebruik een borsteltje om handig te werken.", 0),
                    RecipeStep("Bak de kuipjes in de oven tot ze luchtig en goudbruin zijn. Reken een baktijd van ongeveer 20 minuten op 180°C.", 1200),
                    RecipeStep("Meng het gehakt, samen met een ei en het broodkruim. Kruid met wat peper van de molen en zout. Rol het vleesmengsel tot identieke kleine balletjes van ongeveer een centimeter diameter.", 0),
                    RecipeStep("Breng een deel van de (gezeefde) kippenbouillon aan de kook. Laat de balletjes daarin enkele minuten garen op een matig vuur.", 0),
                    RecipeStep("Smelt een klont boter in een pan en bak de stukjes paddenstoel. Voeg pas tijdens het bakken een geplet teentje knoflook toe zodat de look niet kan verbranden. Voeg een beetje peper en zout toe. Laat de champignons kleuren en zet ze opzij.", 0),
                    RecipeStep("Pluk al het vlees van het karkas van de kip. Beslis hoe je de kip wil serveren: in eerder grote stukken, hapklare brokken of extra fijn. Zet het vlees even opzij.", 0),
                    RecipeStep("Start de bereiding van de saus met een roux. Smelt de boter in een kookpot en voeg de bloem eraan toe. Laat het bloemmengsel al roerend 'opdrogen', maar let erop dat de roux niet aanbakt.", 0),
                    RecipeStep("Zodra je een lichte biscuitgeur ruikt, schenk je een deel van de gezeefde kippenbouillon in de pot. Blijf geduldig doorroeren met een garde om klontertjes te vermijden.", 0),
                    RecipeStep("Schenk er af en toe wat bouillon bij, en wacht tot de saus bubbelt. Ga door tot je een gebonden, maar tegelijk voldoende vloeibare saus krijgt.", 0),
                    RecipeStep("Voeg tenslotte een scheutje madeira (of sherry) toe en ook de room. Roer de saus zorgvuldig.", 0),
                    RecipeStep("Voeg de gebakken paddenstoelen, de stukjes kip en de gehaktballetjes toe aan de saus. Druppel wat vers citroensap in het mengsel en kruid de vol au vent naar smaak met wat peper, zout en versgeraspte nootmuskaat. Roer, proef en voeg naar smaak een extra beetje madeirawijn.", 0),
                    RecipeStep("Snij de boter in kleine blokjes.", 0),
                    RecipeStep("Scheid de eieren en doe de dooiers in een pan. (Gebruik bij voorkeur een pan met een bolle onderzijde.) Hou één eierdopje bij om de vloeistof voor deze saus af te meten. Voeg voor elke dooier één dopje vloeistof toe. Ik gebruik 2/3 witte wijn, en 1/3 water. (Het eiwit wordt niet gebruikt.)", 0),
                    RecipeStep("Klop het mengsel alvast schuimig, alvorens je het op het fornuis zet.", 0),
                    RecipeStep("Zet de pan op een zacht vuur en blijf zonder ophouden kloppen met de garde. Klop tegen een flinke snelheid in een 8-vorm, of draai je pan voortdurend een kwartslag naar links en naar rechts. Neem de pan af en toe weg van het vuur. Blijf doorgaan tot je een licht gebonden schuimige saus krijgt, waarin de garde sporen nalaat. Laat ze in geen geval aanbranden of te heet worden.", 0),
                    RecipeStep("Neem de pan van het vuur. Voeg de boterblokjes in delen toe aan de warme schuimige saus. Blijf intussen stevig kloppen met de garde.", 0),
                    RecipeStep("Knijp het citroensap erbij. Voeg ook een voorzichtig snuifje cayennepeper en wat zout toe.", 0),
                    RecipeStep("Roer en proef of de smaken in balans zijn.", 0),
                    RecipeStep("Snij het dekseltje uit elk bladerdeeggebak, en schep een flinke portie van de velouté met kip, balletjes en paddenstoelen in en rond het koekje.", 0),
                    RecipeStep("Lepel wat luchtige hollandaise over elk koninginnenhapje en zet het dekseltje van bladerdeeg erbovenop.", 0),
                    RecipeStep("Werk elk bord af met een toef pittige tuinkers en geniet.", 0)
                ),
                listOf(
                    Ingredient("selder", 1.0, RecipeUnit.STUKS),
                    Ingredient("wortel", 0.5, RecipeUnit.LEEG),
                    Ingredient("preistengel", 0.5, RecipeUnit.LEEG),
                    Ingredient("ui", 0.75, RecipeUnit.LEEG),
                    Ingredient("look", 1.25, RecipeUnit.STUKS),
                    Ingredient("tijm", 1.0, RecipeUnit.STUKS),
                    Ingredient("laurier", 1.0, RecipeUnit.STUKS),
                    Ingredient("rozemarijn", 0.5, RecipeUnit.STUKS),
                    Ingredient("peterseliestengel", 0.25, RecipeUnit.STUKS),
                    Ingredient("zout", 0.0, RecipeUnit.LEEG),
                    Ingredient("zwarte peper", 3.75, RecipeUnit.STUKS),
                    Ingredient("kruidnagel", 0.5, RecipeUnit.LEEG),
                    Ingredient("hoevekip", 0.25, RecipeUnit.LEEG),
                    Ingredient("bladerdeeg", 0.5, RecipeUnit.STUKS),
                    Ingredient("eigeel", 0.25, RecipeUnit.LEEG),
                    Ingredient("beetje water", 0.0, RecipeUnit.LEEG),
                    Ingredient("gehakt", 75.0, RecipeUnit.G),
                    Ingredient("broodkruim", 0.25, RecipeUnit.EETLEPELS),
                    Ingredient("peper", 0.0, RecipeUnit.LEEG),
                    Ingredient("kleine Parijse champignons", 62.5, RecipeUnit.G),
                    Ingredient("boter", 0.25, RecipeUnit.KLONTJES),
                    Ingredient("boter", 77.5, RecipeUnit.G),
                    Ingredient("bloem", 20.0, RecipeUnit.G),
                    Ingredient("kippenbouillon die je net zelf hebt gemaakt", 0.375, RecipeUnit.L),
                    Ingredient("madeirawijn of sherry", 0.0, RecipeUnit.SCHEUTJES),
                    Ingredient("room", 0.375, RecipeUnit.DL),
                    Ingredient("citroen", 0.25, RecipeUnit.LEEG),
                    Ingredient("nootmuskaat", 0.0, RecipeUnit.LEEG),
                    Ingredient("eierdooier", 0.75, RecipeUnit.LEEG),
                    Ingredient("witte wijn", 0.5, RecipeUnit.DOPJES),
                    Ingredient("water", 0.25, RecipeUnit.DOPJES),
                    Ingredient("cayennepeper", 0.0, RecipeUnit.SNUIFJES),
                    Ingredient("zout", 0.0, RecipeUnit.SNUIFJES)
                ),
                listOf(Tag.DINNER),
                null
            ),
            Recipe(
                "Soep met courgette en basilicum",
                listOf(
                    RecipeStep("Ontdooi en verwarm de groentebouillon. Je kan ook zelf een ketel verse bouillon klaarmaken.", 0),
                    RecipeStep("Pel de uien en snijd ze grof. Kneus en pel de tenen knoflook.", 0),
                    RecipeStep("Spoel de courgettes en snijd ze in stukken.", 0),
                    RecipeStep("Verwarm een bodem olijfolie in een soepketel. Stoof de uien zonder ze te kleuren en voeg de knoflook toe.", 0),
                    RecipeStep("Doe de courgettes in de soepketel en laat mee stoven.", 0),
                    RecipeStep("Kruid met een snuif rozemarijn en tijm. Giet de warme bouillon erbij. Breng aan de kook en laat op een zacht vuur garen met het deksel schuin op de pan.", 0),
                    RecipeStep("Spoel de spinazie en basilicum. Houd een paar blaadjes basilicum apart voor de afwerking.", 0),
                    RecipeStep("Breng water met een snuifje bicarbonaat aan de kook in een tweede kookpot. Kook de spinazie en basilicum tot puree.", 0),
                    RecipeStep("Verwarm de ovengrill. Snijd het brood in plakjes en besprenkel ze met olijfolie. Leg de plakjes op een rooster en bak ze goudbruin onder de grill. Keer ze om en bak de andere kant ook goudbruin.", 0),
                    RecipeStep("Schep de spinazie en basilicum uit het water met een schuimspaan en voeg ze toe aan de soep.", 0),
                    RecipeStep("Mix de soep met een staafmixer en breng op smaak met peper en zout.", 0),
                    RecipeStep("Besmeer de toastjes met geitenkaas. Werk af met versgemalen peper en blaadjes basilicum.", 0),
                    RecipeStep("Serveer de soep in kommen of borden. Voeg een scheutje olijfolie toe en leg er wat basilicumblaadjes op. Serveer de geitenkaas-toastjes erbij.", 0)
                ),
                listOf(
                    Ingredient("verse geitenkaas", 0.25, RecipeUnit.LEEG),
                    Ingredient("courgettes", 1.25, RecipeUnit.LEEG),
                    Ingredient("spinazie", 50.0, RecipeUnit.G),
                    Ingredient("uien", 1.0, RecipeUnit.LEEG),
                    Ingredient("knoflook", 0.5, RecipeUnit.STUKS),
                    Ingredient("basilicum", 0.5, RecipeUnit.STUKS),
                    Ingredient("groentebouillon", 0.75, RecipeUnit.L),
                    Ingredient("ciabatta brood", 0.25, RecipeUnit.LEEG),
                    Ingredient("olijfolie", 0.0, RecipeUnit.LEEG),
                    Ingredient("bicarbonaat", 0.0, RecipeUnit.SNUIFJES),
                    Ingredient("gedroogde rozemarijn", 0.0, RecipeUnit.SNUIFJES),
                    Ingredient("gedroogde tijm", 0.0, RecipeUnit.SNUIFJES),
                    Ingredient("peper", 0.0, RecipeUnit.LEEG),
                    Ingredient("zout", 0.0, RecipeUnit.LEEG)
                ),
                listOf(Tag.SOUP, Tag.APPETIZER),
                null
            )
        )

        RecipeRepository(context).saveAll(recipeList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // we need to do this to respond correctly to clicks on menu items, otherwise it won't be caught
        if(menuBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}