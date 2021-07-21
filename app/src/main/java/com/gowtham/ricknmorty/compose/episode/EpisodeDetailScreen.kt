package com.gowtham.ricknmorty.compose.episode

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.outlined.Today
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gowtham.ricknmorty.compose.characters.CharacterAvatar
import com.gowtham.ricknmorty.compose.common.CharacterTitle
import com.gowtham.ricknmorty.compose.common.FailedComposable
import com.gowtham.ricknmorty.compose.common.InfoRow
import com.gowtham.ricknmorty.utils.Resource
import fragment.EpisodeDetail
import kotlinx.coroutines.launch

@Composable
fun EpisodeDetailScreen(
    episodeId: String,
    episodeName: String,
    viewModel: EpisodeViewModel = hiltViewModel(),
    popBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val episodeState = viewModel.state.collectAsState()

    LaunchedEffect(episodeId) {
        viewModel.setEpisodeId(episodeId)
    }

    val retry: () -> Unit = {
        coroutineScope.launch {
            viewModel.setEpisodeId(episodeId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(episodeName) },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        when (episodeState.value) {
            is Resource.Success -> EpisodeDetail(episodeState.value.data)
            is Resource.Error -> FailedComposable(
                errorMessage = episodeState.value.message.toString(), retry = retry
            )
            else -> CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .size(35.dp)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun EpisodeDetail(episode: EpisodeDetail?) {
    val listInfoTitle = listOf("Name", "Air time", "Code")
    val listInfo = listOf(
        episode?.name.toString(),
        episode?.air_date.toString(),
        episode?.episode.toString(),
    )
    val listInfoIcon = listOf(
        Icons.Outlined.Info, Icons.Outlined.Today,
        Icons.Outlined.Tag
    )

    Surface {
        LazyColumn {
            episode?.let { episodeDetail ->
                item {
                    CharacterTitle("INFO")
                }
                items(listInfoTitle.size) { index ->
                    InfoRow(
                        imageVector = listInfoIcon[index],
                        title = listInfoTitle[index],
                        subTitle = listInfo[index]
                    )
                    if (index != listInfoTitle.lastIndex)
                        Divider()
                }
                item {
                    CharacterTitle("CHARACTERS")
                }
                items(episodeDetail.characters.size) { index ->
                    val character = episodeDetail.characters[index]
                    character?.let {
                        CharacterRow(character)
                        if (index < episode.characters.lastIndex)
                            Divider(modifier = Modifier.padding(horizontal = 12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterRow(character: EpisodeDetail.Character) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CharacterAvatar(
            name = character.name.toString(),
            url = character.image,
            size = 40.dp
        )
        Text(
            character.name.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = MaterialTheme.typography.subtitle1,
        )
    }
}
