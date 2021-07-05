package com.gowtham.ricknmorty.compose.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.gowtham.ricknmorty.MainViewModel
import fragment.CharacterDetail

@Composable
fun CharactersScreen(viewModel: MainViewModel) {
    val lazyCharacterList = viewModel.characters.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Characters") }) },
    ) {
        LazyColumn(contentPadding = it) {
            items(lazyCharacterList) { character ->
                character?.let {
                    CharactersListRowView(character)
                }
            }
        }
    }
}

@Composable
fun CharactersListRowView(character: CharacterDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { })
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier.size(55.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            character.image?.let {
                Image(
                    painter = rememberCoilPainter(it, fadeIn = true, fadeInDurationMs = 400),
                    modifier = Modifier.size(50.dp),
                    contentDescription = character.name
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                character.name ?: "Name unavailable",
                style = MaterialTheme.typography.h6, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "${character.episode.size} episode(s)",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 12.dp))
}
