package com.gowtham.ricknmorty.compose.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CharacterTitle(title: String) {
    val txtBgColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.Black
    Text(
        text = title, style = MaterialTheme.typography.subtitle1,
        modifier = Modifier
            .fillMaxWidth()
            .background(txtBgColor.copy(alpha = 0.6f))
            .padding(start = 12.dp, top = 18.dp, bottom = 8.dp),
    )
}

@Composable
fun InfoRow(imageVector: ImageVector, title: String, subTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
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
        Spacer(modifier = Modifier.width(40.dp))
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
        Text(text = errorMessage, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = retry) {
            Text(text = "Retry")
        }
    }
}
