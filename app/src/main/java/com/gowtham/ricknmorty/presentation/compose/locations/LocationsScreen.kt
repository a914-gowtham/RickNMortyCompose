package com.gowtham.ricknmorty.presentation.compose.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import fragment.LocationDetail

@Composable
fun LocationsScreen(
    viewModel: MainViewModel,
    bottomBar: @Composable () -> Unit,
    onItemClickListener: (character: LocationDetail) -> Unit
) {
    val lazyLocationList = viewModel.locations.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Locations") }) },
        bottomBar = bottomBar,
        modifier = Modifier.fillMaxSize()
    ) {
        if (lazyLocationList.loadState.refresh is LoadState.Loading) {
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
            items(lazyLocationList) { location ->
                location?.let {
                    LocationRow(location, onItemClickListener)
                }
            }
            if (lazyLocationList.loadState.append is LoadState.Loading) {
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
            if (lazyLocationList.loadState.append is LoadState.Error) {
                val state = lazyLocationList.loadState.append as LoadState.Error
                val errorMessage = state.error.localizedMessage ?: "unknown error occurred"
                item {
                    ErrorView(errorMessage) {
                        lazyLocationList.retry()
                    }
                }
            }
        }
        if (lazyLocationList.loadState.refresh is LoadState.Error) {
            val state = lazyLocationList.loadState.refresh as LoadState.Error
            val errorMessage = state.error.localizedMessage ?: "unknown error occurred"
            ErrorView(errorMessage) {
                lazyLocationList.retry()
            }
        }
    }
}

@Composable
fun LocationRow(
    location: LocationDetail,
    onItemClickListener: (character: LocationDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClickListener(location) }
            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
    ) {
        Text(
            text = location.name ?: "Location name unavailable",
            style = MaterialTheme.typography.h6,
            maxLines = 1, overflow = TextOverflow.Ellipsis
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "${location.residents.size} resident(s)",
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Divider()
    }
}
