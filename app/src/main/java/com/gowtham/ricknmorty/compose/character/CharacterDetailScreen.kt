package com.gowtham.ricknmorty.compose.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    Surface() {
        LazyColumn {
            character?.let {
                item {
                    Text(
                        text = "Mugshot", style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 6.dp)
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(color = Color.White) {
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
                item {
                    Text(
                        text = "Info", style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 6.dp)
                    )
                }
                item {
                    InfoRow(
                        imageVector = Icons.Default.Android,
                        title = "Species",
                        subTitle = it.species.toString()
                    )
                    InfoRow(
                        imageVector = Icons.Outlined.Wc,
                        title = "Gender",
                        subTitle = it.gender.toString()
                    )
                    InfoRow(
                        imageVector = Icons.Default.StackedLineChart,
                        title = "Status",
                        subTitle = it.status.toString()
                    )
                    InfoRow(
                        imageVector = Icons.Default.Map,
                        title = "Location",
                        subTitle = it.location?.name.toString()
                    )
                    InfoRow(
                        imageVector = Icons.Default.NearMe,
                        title = "Origin",
                        subTitle = it.origin?.name.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(imageVector: ImageVector, title: String, subTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector, contentDescription = title,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            modifier = Modifier.weight(weight = 1f),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = subTitle,
            modifier = Modifier.sizeIn(maxWidth = 160.dp),
            style = MaterialTheme.typography.body1
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
