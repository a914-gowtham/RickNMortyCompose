package com.gowtham.ricknmorty.compose.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gowtham.ricknmorty.MainViewModel
import com.gowtham.ricknmorty.utils.formatToDate
import fragment.EpisodeDetail

@Composable
fun EpisodesScreen(viewModel: MainViewModel) {
    val lazyEpisodeList = viewModel.episodes.collectAsLazyPagingItems()

    Scaffold(topBar = { TopAppBar({ Text("Episodes") }) }) {
        LazyColumn(contentPadding = it) {
            items(lazyEpisodeList) { episode ->
                episode?.let {
                    EpisodeRow(episode)
                }
            }
        }
    }
}

@Composable
fun EpisodeRow(episode: EpisodeDetail) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* handle onclick */ }
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
        Divider(modifier = Modifier.padding(horizontal = 12.dp))
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}
*/
