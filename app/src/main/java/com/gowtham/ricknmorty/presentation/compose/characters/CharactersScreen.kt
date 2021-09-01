package com.gowtham.ricknmorty.presentation.compose.characters

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.gowtham.ricknmorty.presentation.MainViewModel
import fragment.CharacterDetail

@Composable
fun CharactersScreen(
    viewModel: MainViewModel,
    bottomBar: @Composable () -> Unit,
    onClickListener: (character: CharacterDetail) -> Unit
) {
    val lazyCharacterList = viewModel.characters.collectAsLazyPagingItems()

    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
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
            textAlign = TextAlign.Center, fontSize = 16.sp
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
            .clickable(
                onClick = {
                    onClickListener(character)
                }
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CharacterAvatar(name = character.name.toString(), url = character.image, size = 55.dp)

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

@Composable
fun CharacterAvatar(name: String, url: String?, size: Dp) {
    Surface(
        modifier = Modifier.size(size),
        shape = CircleShape,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
    ) {
        url?.let {
            Image(
                painter = rememberImagePainter(
                    data = it,
                    imageLoader = LocalImageLoader.current,
                    builder = {
                        crossfade(durationMillis = 400)
                        placeholder(0)
                    }
                ),
                modifier = Modifier.size(size),
                contentDescription = name
            )
        }
    }
}
