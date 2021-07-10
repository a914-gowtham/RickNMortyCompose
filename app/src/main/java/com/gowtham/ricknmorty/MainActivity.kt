package com.gowtham.ricknmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gowtham.ricknmorty.compose.character.CharacterDetailScreen
import com.gowtham.ricknmorty.compose.characters.CharactersScreen
import com.gowtham.ricknmorty.compose.theme.TAppTheme
import dagger.hilt.android.AndroidEntryPoint
import fragment.CharacterDetail

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    object CharactersScreen : Screens("Characters", "Characters", Icons.Default.Person)
    object EpisodesScreen : Screens("Episodes", "Episodes",  Icons.Default.Tv)
    object LocationsScreen : Screens("Locations", "Locations",  Icons.Default.LocationOn)
    object CharacterDetailsScreen : Screens("CharacterDetails", "CharacterDetails")
    object EpisodeDetailsScreen : Screens("EpisodeDetails", "EpisodeDetails")
    object LocationDetailsScreen : Screens("LocatonDetails", "LocatonDetails")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TAppTheme{
                RickNMortyApp(viewModel)
            }
        }
    }
}

@Composable
fun RickNMortyApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val CHARACTER_KEY="character_detail"

    val bottomNavigationItems = listOf(Screens.CharactersScreen, Screens.EpisodesScreen, Screens.LocationsScreen)
    val bottomBar: @Composable () -> Unit = { MortyBottomNavigation(navController, bottomNavigationItems) }

    NavHost(navController = navController, startDestination = Screens.CharactersScreen.route){
        composable(Screens.CharactersScreen.route){
            CharactersScreen(viewModel,bottomBar){ character->
                navController.navigate(Screens.CharacterDetailsScreen.route+"/${character}" )
            }
        }
        composable(Screens.CharacterDetailsScreen.route+"/$CHARACTER_KEY"){
            CharacterDetailScreen(viewModel,it.arguments?.get(CHARACTER_KEY) as CharacterDetail
            ,popBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun MortyBottomNavigation(navController: NavHostController, items: List<Screens>) {
   BottomNavigation {
       val currentRoute= currentRoute(navController = navController)
       items.forEach { screen->
           BottomNavigationItem(selected = currentRoute == screen.route,
               onClick = {
                   if (currentRoute != screen.route) {
                       navController.navigate(screen.route) {
                           popUpTo(navController.graph.startDestinationId)
                           launchSingleTop = true
                       }
                   }
               },
           icon = { screen.icon?.let { Icon(screen.icon, contentDescription = screen.label) } },
           label = { Text(screen.label) },
           )
       }
   }
    

}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TAppTheme {
        Greeting("Android")
    }
}
