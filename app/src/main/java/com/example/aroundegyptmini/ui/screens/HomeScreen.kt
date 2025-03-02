package com.example.aroundegyptmini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aroundegyptmini.R


@Composable
fun TopBar(
    modifier: Modifier = Modifier
){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        Icon(Icons.Default.Menu,
            contentDescription = null,
            )

        SearchBar(
            query = "",
            onQueryChange = { },
            onSearchFocusChange = { },
            onClearQuery = { },
            modifier = modifier.weight(1f)
                .clip(MaterialTheme.shapes.small)
                .padding(dimensionResource(R.dimen.padding_medium)))

        Icon(Icons.Default.Tune, contentDescription = null)

    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
){
    var text by remember { mutableStateOf("Try Luxor") }

    TextField(
        value = text,
        onValueChange = {
            text = query
        },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        colors = TextFieldDefaults.colors(Color.Red),
        modifier = modifier
    )

}

@Composable
fun WelcomeText(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.welcome_title))
        Text(text = stringResource(R.string.welcome_body_text))
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TopBar()
        WelcomeText()

    }
}


@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(query = "", onQueryChange = {}, onSearchFocusChange = {}, onClearQuery = {})
}

@Preview (showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

