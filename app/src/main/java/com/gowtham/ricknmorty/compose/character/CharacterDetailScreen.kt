package com.gowtham.ricknmorty.compose.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.gowtham.ricknmorty.compose.common.CharacterTitle
import com.gowtham.ricknmorty.compose.common.FailedComposable
import com.gowtham.ricknmorty.compose.common.InfoRow
import com.gowtham.ricknmorty.utils.Resource
import fragment.CharacterDetail
import kotlinx.coroutines.launch

@Composable
fun CharacterDetailScreen(
    characterId: String,
    characterName: String,
    viewModel: CharacterViewModel,
    popBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val characterState = viewModel.state.collectAsState()

 /*   LaunchedEffect(characterId) {
        viewModel.setCharacter(characterId)
    }*/

    val retry: () -> Unit = {
        coroutineScope.launch {
            viewModel.setCharacter(characterId)
        }
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
            is Resource.Success -> DetailData(characterState.value.data)
            is Resource.Error -> FailedComposable(
                errorMessage = characterState.value.message.toString(), retry = retry
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
fun DetailData(character: CharacterDetail?) {
    val listInfoTitle = listOf("Species", "Gender", "Status", "Location", "Origin")
    val listInfo = listOf(
        character?.species.toString(),
        character?.gender.toString(),
        character?.status.toString(),
        character?.location?.name.toString(),
        character?.origin?.name.toString()
    )
    val listInfoIcon = listOf(
        Icons.Outlined.SmartToy, Icons.Outlined.Wc, Icons.Outlined.StackedLineChart,
        Icons.Outlined.Map, Icons.Outlined.NearMe
    )

    Surface {
        LazyColumn {
            character?.let {
                item {
                    CharacterTitle("MUGSHOT")
                }
                item {
                    CharacterImage(character)
                }
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
                    CharacterTitle("EPISODES")
                }
                items(character.episode.size) { index ->
                    val episode = it.episode[index]
                    episode?.let {
                        EpisodeRow(episode)
                        if (index < character.episode.lastIndex)
                            Divider(modifier = Modifier.padding(horizontal = 12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterImage(character: CharacterDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface {
            Card(
                modifier = Modifier.size(140.dp),
                shape = RoundedCornerShape(22.dp)
            ) {
                Image(
                    painter = rememberCoilPainter(
                        request = character.image, fadeIn = true,
                        fadeInDurationMs = 400
                    ),
                    contentDescription = character.name,
                )
            }
        }
    }
}

@Composable
fun EpisodeRow(episode: CharacterDetail.Episode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            episode.name.toString(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.width(12.dp))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                episode.air_date.toString(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}
