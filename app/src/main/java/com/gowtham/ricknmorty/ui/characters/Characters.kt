package com.gowtham.ricknmorty.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ricknmorty.ui.theme.TAppTheme

@Composable
fun CharactersScreen(){
    Column(modifier = Modifier.padding(12.dp)) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Hey man")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TAppTheme {
        CharactersScreen()
    }
}