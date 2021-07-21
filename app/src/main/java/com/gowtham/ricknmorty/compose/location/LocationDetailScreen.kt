package com.gowtham.ricknmorty.compose.location

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
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Stream
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
import fragment.LocationDetail
import kotlinx.coroutines.launch

@Composable
fun LocationDetailScreen(
    locationId: String,
    locationName: String,
    viewModel: LocationViewModel = hiltViewModel(),
    popBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val locationState = viewModel.state.collectAsState()

    LaunchedEffect(locationId) {
        viewModel.setLocation(locationId)
    }

    val retry: () -> Unit = {
        coroutineScope.launch {
            viewModel.setLocation(locationId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(locationName) },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        when (locationState.value) {
            is Resource.Success -> LocationDetail(locationState.value.data)
            is Resource.Error -> FailedComposable(
                errorMessage = locationState.value.message.toString(), retry = retry
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
fun LocationDetail(location: LocationDetail?) {
    val listInfoTitle = listOf(
        "Name", "Type",
        "Dimension"
    )
    val listInfo = listOf(
        location?.name.toString(),
        location?.type.toString(),
        location?.dimension.toString(),
    )
    val listInfoIcon = listOf(
        Icons.Outlined.Info, Icons.Outlined.Public,
        Icons.Outlined.Stream
    )

    Surface {
        LazyColumn {
            location?.let { locationDetail ->
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
                    CharacterTitle("RESIDENTS")
                }
                items(locationDetail.residents.size) { index ->
                    val resident = locationDetail.residents[index]
                    resident?.let {
                        ResidentsRow(resident)
                        if (index < locationDetail.residents.lastIndex)
                            Divider(modifier = Modifier.padding(horizontal = 12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ResidentsRow(resident: LocationDetail.Resident) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CharacterAvatar(
            name = resident.name.toString(),
            url = resident.image,
            size = 40.dp
        )
        Text(
            resident.name.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = MaterialTheme.typography.subtitle1,
        )
    }
}
