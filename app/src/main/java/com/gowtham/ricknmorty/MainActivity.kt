package com.gowtham.ricknmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gowtham.ricknmorty.compose.characters.CharactersScreen
import com.gowtham.ricknmorty.compose.episodes.EpisodesScreen
import com.gowtham.ricknmorty.compose.locations.LocationsScreen
import com.gowtham.ricknmorty.compose.theme.TAppTheme
import com.gowtham.ricknmorty.navigation.AppNavigation
import com.gowtham.ricknmorty.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var splashScreen: SplashScreen

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
        splashScreen = installSplashScreen()
        setContent {
            AppContent {
                splashScreen.setKeepVisibleCondition(it)
            }
        }
    }

    @Composable
    fun AppContent(splashScreenVisibleCondition: (SplashScreen.KeepOnScreenCondition) -> Unit) {
        TAppTheme {
            RickNMortyApp(splashScreenVisibleCondition, viewModel)
        }
    }
}

@Composable
fun RickNMortyApp(
    splashScreenVisibleCondition: (SplashScreen.KeepOnScreenCondition) -> Unit,
    viewModel: MainViewModel
) {
    splashScreenVisibleCondition {
        viewModel.splash.value
    }
    val navController = rememberNavController()
    val bottomNavigationItems =
        listOf(Screens.CharactersScreen, Screens.EpisodesScreen, Screens.LocationsScreen)
    val bottomBar: @Composable () -> Unit =
        { MortyBottomNavigation(navController, bottomNavigationItems) }
    AppNavigation(viewModel, navController, bottomBar,)
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
