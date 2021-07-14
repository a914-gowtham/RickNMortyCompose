package com.gowtham.ricknmorty.compose.character

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.gowtham.ricknmorty.utils.Resource

@Composable
fun CharacterDetailScreen(
    characterId: String,
    characterName: String,
    viewModel: CharacterViewModel = hiltViewModel(),
    popBack: () -> Unit
) {

    val characterState = viewModel.state.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.setCharacter(characterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(characterName) },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        when (characterState.value) {
            is Resource.Error -> Text(text = characterState.value.message.toString())
            is Resource.Success -> Text(text = "Success")
            else -> CircularProgressIndicator()
        }
    }
}
