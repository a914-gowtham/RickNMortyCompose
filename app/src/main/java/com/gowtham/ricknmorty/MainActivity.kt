package com.gowtham.ricknmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.gowtham.ricknmorty.MainActivity.Companion.EPISODE_KEY
import com.gowtham.ricknmorty.MainActivity.Companion.LOCATION_KEY
import com.gowtham.ricknmorty.compose.character.CharacterDetailScreen
import com.gowtham.ricknmorty.compose.characters.CharactersScreen
import com.gowtham.ricknmorty.compose.episodes.EpisodesScreen
import com.gowtham.ricknmorty.compose.locations.LocationsScreen
import com.gowtham.ricknmorty.compose.theme.TAppTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    object CharactersScreen : Screens("Characters", "Characters", Icons.Default.Person)
    object EpisodesScreen : Screens("Episodes", "Episodes", Icons.Default.Tv)
    object LocationsScreen : Screens("Locations", "Locations", Icons.Default.LocationOn)
    object CharacterDetailScreen : Screens("CharacterDetail", "CharacterDetail")
    object EpisodeDetailScreen : Screens("EpisodeDetail", "EpisodeDetail")
    object LocationDetailScreen : Screens("LocationDetail", "LocationDetail")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    companion object {
        const val CHARACTER_KEY = "character_detail"
        const val EPISODE_KEY = "episode_detail"
        const val LOCATION_KEY = "location_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TAppTheme {
                RickNMortyApp(viewModel)
            }
        }
    }
}

@Composable
fun RickNMortyApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val bottomNavigationItems =
        listOf(Screens.CharactersScreen, Screens.EpisodesScreen, Screens.LocationsScreen)
    val bottomBar: @Composable () -> Unit =
        { MortyBottomNavigation(navController, bottomNavigationItems) }

    NavHost(navController = navController, startDestination = Screens.CharactersScreen.route) {
        composable(Screens.CharactersScreen.route) {
            CharactersScreen(viewModel, bottomBar) { character ->
                navController.navigate(
                    Screens.CharacterDetailScreen.route +
                        "?characterId=${character.id}&characterName=${character.name}"
                )
            }
        }
        composable(
            Screens.CharacterDetailScreen.route +
                "?characterId={id}&characterName={name}",
            arguments = listOf(
                navArgument("characterId") { nullable = true },
                navArgument("characterName") { nullable = true }
            )
        ) {
            val characterId = it.arguments?.getString("id").toString()
            val characterName = it.arguments?.getString("name").toString()
            CharacterDetailScreen(
                characterName = characterName,
                characterId = characterId,
            ) {
                navController.popBackStack()
            }
        }
        composable(Screens.EpisodesScreen.route) {
            EpisodesScreen(viewModel, bottomBar) { episode ->
                navController.navigate(Screens.EpisodeDetailScreen.route + "/$episode")
            }
        }
        composable(Screens.EpisodeDetailScreen.route + "/$EPISODE_KEY") {
//            Screens.EpisodeDetailScreen(viewModel, it.arguments?.get(CHARACTER_KEY) as EpisodeDetail, popBack = { navController.popBackStack() })
        }
        composable(Screens.LocationsScreen.route) {
            LocationsScreen(viewModel, bottomBar) { episode ->
                navController.navigate(Screens.LocationDetailScreen.route + "/$episode")
            }
        }
        composable(Screens.LocationDetailScreen.route + "/$LOCATION_KEY") {
//            Screens.LocationDetailScreen(viewModel, it.arguments?.get(LOCATION_KEY) as LocationDetail, popBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun MortyBottomNavigation(navController: NavHostController, items: List<Screens>) {
    BottomNavigation {
        val currentRoute = currentRoute(navController = navController)
        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    screen.icon?.let {
                        Icon(
                            screen.icon,
                            contentDescription = screen.label
                        )
                    }
                },
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
