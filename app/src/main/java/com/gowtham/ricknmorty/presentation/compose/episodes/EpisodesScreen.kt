package com.gowtham.ricknmorty.presentation.compose.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gowtham.ricknmorty.presentation.MainViewModel
import com.gowtham.ricknmorty.presentation.compose.characters.ErrorView
import com.gowtham.ricknmorty.utils.formatToDate
import fragment.EpisodeDetail

@Composable
fun EpisodesScreen(
    viewModel: MainViewModel,
    bottomBar: @Composable () -> Unit,
    onItemClickListener: (character: EpisodeDetail) -> Unit
) {
    val lazyEpisodeList = viewModel.episodes.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Episodes") }) },
        bottomBar = bottomBar,
        modifier = Modifier.fillMaxSize()
    ) {
        if (lazyEpisodeList.loadState.refresh is LoadState.Loading) {
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
            items(lazyEpisodeList) { episode ->
                episode?.let {
                    EpisodeRow(episode, onItemClickListener)
                }
            }
            if (lazyEpisodeList.loadState.append is LoadState.Loading) {
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
            if (lazyEpisodeList.loadState.append is LoadState.Error) {
                val state = lazyEpisodeList.loadState.append as LoadState.Error
                val errorMessage = state.error.localizedMessage ?: "unknown error occurred"
                item {
                    ErrorView(errorMessage) {
                        lazyEpisodeList.retry()
                    }
                }
            }
        }
        if (lazyEpisodeList.loadState.refresh is LoadState.Error) {
            val state = lazyEpisodeList.loadState.refresh as LoadState.Error
            val errorMessage = state.error.localizedMessage ?: "unknown error occurred"
            ErrorView(errorMessage) {
                lazyEpisodeList.retry()
            }
        }
    }
}

@Composable
fun EpisodeRow(episode: EpisodeDetail, onItemClickListener: (character: EpisodeDetail) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClickListener(episode) }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f, fill = true)
                    .padding(
                        end = 8.dp
                    )
            ) {
                Text(
                    text = episode.name ?: "Episode name unavailable",
                    style = MaterialTheme.typography.h6,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = episode.episode ?: "Code unavailable",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Text(
                text = episode.air_date?.formatToDate() ?: "No airtime",
                style = MaterialTheme.typography.subtitle1
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 6.dp))
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}
*/
