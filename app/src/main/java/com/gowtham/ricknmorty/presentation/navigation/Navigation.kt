package com.gowtham.ricknmorty.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.gowtham.ricknmorty.presentation.MainActivity
import com.gowtham.ricknmorty.presentation.MainViewModel
import com.gowtham.ricknmorty.presentation.compose.character.CharacterDetailScreen
import com.gowtham.ricknmorty.presentation.compose.characters.CharactersScreen
import com.gowtham.ricknmorty.presentation.compose.episode.EpisodeDetailScreen
import com.gowtham.ricknmorty.presentation.compose.episodes.EpisodesScreen
import com.gowtham.ricknmorty.presentation.compose.location.LocationDetailScreen
import com.gowtham.ricknmorty.presentation.compose.locations.LocationsScreen
import com.gowtham.ricknmorty.utils.Utils

@ExperimentalAnimationApi
@Composable
fun AppNavigation(
    viewModel: MainViewModel,
    navController: NavHostController,
    bottomBar: @Composable () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screens.CharactersScreen.route
    ) {

        composable(
            Screens.CharactersScreen.route,
        ) {
            CharactersScreen(viewModel, bottomBar) { character ->
                navController.navigate(
                    Screens.CharacterDetailScreen.withArgs(
                        args = mapOf(
                            MainActivity.CHARACTER_ID to character.id.toString(),
                            MainActivity.CHARACTER_NAME to character.name.toString(),
                        )
                    )
                )
            }
        }
        composable(
            Screens.CharacterDetailScreen.route +
                "?${MainActivity.CHARACTER_ID}={id}&${MainActivity.CHARACTER_NAME}={name}",
            arguments = listOf(
                navArgument(MainActivity.CHARACTER_ID) { nullable = true },
                navArgument(MainActivity.CHARACTER_NAME) { nullable = true }
            ),
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
                            MainActivity.EPISODE_ID to episode.id.toString(),
                            MainActivity.EPISODE_NAME to episode.name.toString(),
                        )
                    )
                )
            }
        }
        composable(
            Screens.EpisodeDetailScreen.route +
                "?${MainActivity.EPISODE_ID}={id}&${MainActivity.EPISODE_NAME}={name}",
            arguments = listOf(
                navArgument(MainActivity.EPISODE_ID) { nullable = true },
                navArgument(MainActivity.EPISODE_NAME) { nullable = true }
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
                            MainActivity.LOCATION_ID to location.id.toString(),
                            MainActivity.LOCATION_NAME to location.name.toString(),
                        )
                    )
                )
            }
        }
        composable(
            Screens.LocationDetailScreen.route +
                "?${MainActivity.LOCATION_ID}={id}&${MainActivity.LOCATION_NAME}={name}",
            arguments = listOf(
                navArgument(MainActivity.LOCATION_ID) { nullable = true },
                navArgument(MainActivity.LOCATION_NAME) { nullable = true }
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

const val ANIMATION_DURATION= 300
@ExperimentalAnimationApi
fun enterTransition(desRoute: String, initial: NavBackStackEntry): EnterTransition? {
    return when (initial.destination.route) {
        desRoute ->
            slideInHorizontally(
                initialOffsetX = { ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}

@ExperimentalAnimationApi
fun exitTransition(desRoute: String, target: NavBackStackEntry): ExitTransition? {
    return when (target.destination.route) {
        desRoute ->
            slideOutHorizontally(
                targetOffsetX = { -ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}

@ExperimentalAnimationApi
fun popEnterTransition(desRoute: String, initial: NavBackStackEntry): EnterTransition? {
    return when (initial.destination.route) {
        desRoute ->
            slideInHorizontally(
                initialOffsetX = { -ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}

@ExperimentalAnimationApi
fun popExitAnimation(desRoute: String, target: NavBackStackEntry): ExitTransition? {
    return when (target.destination.route) {
        desRoute ->
            slideOutHorizontally(
                targetOffsetX = { ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}
