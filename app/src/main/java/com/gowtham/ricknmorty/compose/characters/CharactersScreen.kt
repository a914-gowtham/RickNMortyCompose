package com.gowtham.ricknmorty.compose.characters

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.gowtham.ricknmorty.MainViewModel
import fragment.CharacterDetail

@Composable
fun CharactersScreen(
    viewModel: MainViewModel,
    bottomBar: @Composable () -> Unit,
    onClickListener: (character: CharacterDetail) -> Unit
) {
    val lazyCharacterList = viewModel.characters.collectAsLazyPagingItems()
    Scaffold(
        topBar = { TopAppBar(title = { Text("Characters") }) },
        bottomBar = bottomBar,
        modifier = Modifier.fillMaxSize()
    ) {
        if (lazyCharacterList.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .size(35.dp)
                        .wrapContentSize(Alignment.Center),
                    strokeWidth = 5.dp
                )
        }
        LazyColumn(contentPadding = it) {
            items(lazyCharacterList) { character ->
                character?.let {
                    CharactersListRowView(character, onClickListener)
                }
            }
            if (lazyCharacterList.loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(80.dp)
                            .padding(16.dp)
                            .wrapContentHeight()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        strokeWidth = 4.5.dp
                    )
                }
            }
            if (lazyCharacterList.loadState.append is LoadState.Error) {
                val state = lazyCharacterList.loadState.append as LoadState.Error
                val errorMessage = state.error.localizedMessage ?: "unknown error occurred"
                item {
                    ErrorView(errorMessage) {
                        lazyCharacterList.retry()
                    }
                }
            }
        }
        if (lazyCharacterList.loadState.refresh is LoadState.Error) {
            val state = lazyCharacterList.loadState.refresh as LoadState.Error
            val errorMessage = state.error.localizedMessage ?: "unknown error occurred"
            ErrorView(errorMessage) {
                lazyCharacterList.retry()
            }
        }
    }
}

@Composable
fun ErrorView(errorMessage: String, onBtnClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .requiredHeight(80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            errorMessage, style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = onBtnClick
        ) {
            Text("Retry")
        }
    }
}

@Composable
fun CharactersListRowView(
    character: CharacterDetail,
    onClickListener: (character: CharacterDetail) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClickListener(character)
            })
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
    Divider(modifier = Modifier.padding(horizontal = 6.dp))
}
