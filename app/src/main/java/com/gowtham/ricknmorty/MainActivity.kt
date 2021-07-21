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
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.gowtham.ricknmorty.MainActivity.Companion.CHARACTER_ID
import com.gowtham.ricknmorty.MainActivity.Companion.CHARACTER_NAME
import com.gowtham.ricknmorty.MainActivity.Companion.EPISODE_ID
import com.gowtham.ricknmorty.MainActivity.Companion.EPISODE_NAME
import com.gowtham.ricknmorty.MainActivity.Companion.LOCATION_ID
import com.gowtham.ricknmorty.MainActivity.Companion.LOCATION_NAME
import com.gowtham.ricknmorty.compose.character.CharacterDetailScreen
import com.gowtham.ricknmorty.compose.characters.CharactersScreen
import com.gowtham.ricknmorty.compose.episode.EpisodeDetailScreen
import com.gowtham.ricknmorty.compose.episodes.EpisodesScreen
import com.gowtham.ricknmorty.compose.location.LocationDetailScreen
import com.gowtham.ricknmorty.compose.locations.LocationsScreen
import com.gowtham.ricknmorty.compose.theme.TAppTheme
import com.gowtham.ricknmorty.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    object CharactersScreen : Screens("Characters", "Characters", Icons.Default.Person)
    object EpisodesScreen : Screens("Episodes", "Episodes", Icons.Default.OndemandVideo)
    object LocationsScreen : Screens("Locations", "Locations", Icons.Default.Map)
    object CharacterDetailScreen : Screens("CharacterDetail", "CharacterDetail")
    object EpisodeDetailScreen : Screens("EpisodeDetail", "EpisodeDetail")
    object LocationDetailScreen : Screens("LocationDetail", "LocationDetail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }

    fun withArgs(args: Map<String, String>): String {
        return buildString {
            append(route)
            append("?")
            val iterator = args.iterator()
            while (iterator.hasNext()) {
                val map = iterator.next()
                append(map.key + "=" + map.value)
                if (iterator.hasNext())
                    append("&")
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    companion object {
        const val CHARACTER_ID = "character_id"
        const val EPISODE_ID = "episode_id"
        const val LOCATION_ID = "location_id"
        const val CHARACTER_NAME = "character_name"
        const val EPISODE_NAME = "episode_name"
        const val LOCATION_NAME = "location_name"
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
                    Screens.CharacterDetailScreen.withArgs(
                        args = mapOf(
                            CHARACTER_ID to character.id.toString(),
                            CHARACTER_NAME to character.name.toString(),
                        )
                    )
                )
            }
        }
        composable(
            Screens.CharacterDetailScreen.route +
                "?$CHARACTER_ID={id}&$CHARACTER_NAME={name}",
            arguments = listOf(
                navArgument(CHARACTER_ID) { nullable = true },
                navArgument(CHARACTER_NAME) { nullable = true }
            )
        ) {
            CharacterDetailScreen(
                characterName = Utils.getStringArg("name", it),
                characterId = Utils.getStringArg("id", it),
            ) {
                navController.popBackStack()
            }
        }
        composable(Screens.EpisodesScreen.route) {
            EpisodesScreen(viewModel, bottomBar) { episode ->
                navController.navigate(
                    Screens.EpisodeDetailScreen.withArgs(
                        args = mapOf(
                            EPISODE_ID to episode.id.toString(),
                            EPISODE_NAME to episode.name.toString(),
                        )
                    )
                )
            }
        }
        composable(
            Screens.EpisodeDetailScreen.route +
                "?$EPISODE_ID={id}&$EPISODE_NAME={name}",
            arguments = listOf(
                navArgument(EPISODE_ID) { nullable = true },
                navArgument(EPISODE_NAME) { nullable = true }
            )
        ) {
            EpisodeDetailScreen(
                episodeName = Utils.getStringArg("name", it),
                episodeId = Utils.getStringArg("id", it),
            ) {
                navController.popBackStack()
            }
        }
        composable(Screens.LocationsScreen.route) {
            LocationsScreen(viewModel, bottomBar) { location ->
                navController.navigate(
                    Screens.LocationDetailScreen.withArgs(
                        args = mapOf(
                            LOCATION_ID to location.id.toString(),
                            LOCATION_NAME to location.name.toString(),
                        )
                    )
                )
            }
        }
        composable(
            Screens.LocationDetailScreen.route +
                "?$LOCATION_ID={id}&$LOCATION_NAME={name}",
            arguments = listOf(
                navArgument(LOCATION_ID) { nullable = true },
                navArgument(LOCATION_NAME) { nullable = true }
            )
        ) {
            LocationDetailScreen(
                locationName = Utils.getStringArg("name", it),
                locationId = Utils.getStringArg("id", it),
            ) {
                navController.popBackStack()
            }
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
