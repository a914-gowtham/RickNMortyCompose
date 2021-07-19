package com.gowtham.ricknmorty.compose.episode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.gowtham.ricknmorty.utils.Resource
import fragment.CharacterDetail
import kotlinx.coroutines.launch

@Composable
fun CharacterDetailScreen(
    characterId: String,
    characterName: String,
    viewModel: CharacterViewModel = hiltViewModel(),
    popBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val characterState = viewModel.state.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.setCharacter(characterId)
    }

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
        character?.species.toString(),
        character?.status.toString(),
        character?.location?.name.toString(),
        character?.origin?.name.toString()
    )
    val listInfoIcon = listOf(
        Icons.Outlined.SmartToy, Icons.Outlined.Wc, Icons.Outlined.StackedLineChart,
        Icons.Outlined.Map, Icons.Outlined.NearMe
    )

    Surface() {
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
                    CharacterInfoRow(
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
                items(it.episode.size) { index ->
                    val episode = it.episode[index]
                    episode?.let {
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

                        if (index != listInfoTitle.lastIndex)
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
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface() {
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
fun CharacterInfoRow(imageVector: ImageVector, title: String, subTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector, contentDescription = title,
            modifier = Modifier.size(26.dp),
            tint = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.width(80.dp))
        Text(
            text = subTitle,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun FailedComposable(errorMessage: String, retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMessage)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = retry) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun CharacterTitle(title: String) {
    Text(
        text = title, style = MaterialTheme.typography.subtitle1,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.6f))
            .padding(start = 12.dp, top = 12.dp, bottom = 6.dp),
    )
}
