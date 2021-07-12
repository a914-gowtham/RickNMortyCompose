package com.gowtham.ricknmorty.compose.character

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import com.gowtham.ricknmorty.MainViewModel
import fragment.CharacterDetail

@Composable
fun CharacterDetailScreen(viewModel: MainViewModel, character: CharacterDetail, popBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character.name ?: "Name unavailable") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
    }
}
