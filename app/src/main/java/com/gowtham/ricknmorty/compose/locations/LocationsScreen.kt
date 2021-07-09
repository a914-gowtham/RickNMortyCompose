package com.gowtham.ricknmorty.compose.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gowtham.ricknmorty.MainViewModel
import fragment.EpisodeDetail
import fragment.LocationDetail

@Composable
fun LocationsScreen(viewModel: MainViewModel) {
    val lazyEpisodeList = viewModel.locations.collectAsLazyPagingItems()

    Scaffold(topBar = { TopAppBar({ Text("Locations") }) }) {
        LazyColumn(contentPadding = it) {
            items(lazyEpisodeList){ location->
                location?.let {
                    LocationRow(location)
                }
            }
        }
    }
}

@Composable
fun LocationRow(location: LocationDetail) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
        .padding(start = 12.dp,end = 12.dp,top = 12.dp)) {
        Text(text = location.name ?: "Location name unavailable",
            style = MaterialTheme.typography.h6,
            maxLines = 1, overflow = TextOverflow.Ellipsis)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "${location.residents.size} resident(s)",
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Divider(modifier = Modifier.padding(horizontal = 6.dp))
    }
}
