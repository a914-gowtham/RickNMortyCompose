package com.gowtham.ricknmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

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
