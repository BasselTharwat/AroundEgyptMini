package com.example.aroundegyptmini.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.ui.components.RecentExperiences
import com.example.aroundegyptmini.ui.components.RecommendedExperiences
import com.example.aroundegyptmini.ui.components.SearchExperiences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val homeScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchResultsShow by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val searchBarState = rememberSearchBarState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchText by rememberSaveable { mutableStateOf("") }

    when (homeScreenUiState) {
        is HomeScreenUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
        is HomeScreenUiState.Error -> ErrorScreen(retryAction = {
            viewModel.getExperiences()
            searchResultsShow = false
        },
            modifier.fillMaxSize())
        is HomeScreenUiState.Success -> {
            val successState = homeScreenUiState as HomeScreenUiState.Success

            Column(modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_small))) {
                TopBar(
                    searchBarState = searchBarState,
                    keyboardController = keyboardController,
                    searchText = searchText,
                    onSearchTextChanged = { searchText = it },
                    scope = scope,
                    onCancelClick = {
                        viewModel.clearSearchResults()
                        searchText = ""
                        searchResultsShow = false
                        scope.launch { searchBarState.animateToCollapsed() }
                    },
                    onSearchClick = {
                        if (searchText.isNotEmpty()) {
                            viewModel.searchExperiences(searchText)
                            searchResultsShow = true
                            keyboardController?.hide()
                        }else{
                            searchResultsShow = false
                            keyboardController?.hide()
                            scope.launch { searchBarState.animateToCollapsed() }
                        }
                    }
                )
                Spacer(modifier.padding(dimensionResource(R.dimen.padding_medium)))
                if (!searchResultsShow){
                    WelcomeText()
                    Spacer(modifier.padding(dimensionResource(R.dimen.padding_medium)))
                    RecommendedExperiences(
                        modifier = modifier,
                        recommendedExperiences = successState.recommended,
                        onLikeClick = { id -> viewModel.likeExperience(id) },
                        onExperienceClick = { experience ->
                            viewModel.selectExperience(experience)
                            scope.launch { sheetState.show() }
                        }
                    )
                    Spacer(modifier.padding(dimensionResource(R.dimen.padding_medium)))
                    RecentExperiences(
                        modifier = modifier,
                        recentExperiences = successState.recent,
                        onLikeClick = { id -> viewModel.likeExperience(id) },
                        onExperienceClick = { experience ->
                            viewModel.selectExperience(experience)
                            scope.launch { sheetState.show() }
                        }
                    )
                }else{
                    SearchExperiences(
                        searchExperiences = successState.searchResults,
                        onLikeClick = { id -> viewModel.likeExperience(id) },
                        onExperienceClick = { experience ->
                            viewModel.selectExperience(experience)
                            scope.launch { sheetState.show() }
                        }
                    )
                }
            }

            if (successState.selectedExperience != null) {

                ModalBottomSheet(
                    onDismissRequest = { viewModel.deselectExperience() },
                    sheetState = sheetState,
                    dragHandle = {}
                ) {
                    ExperienceScreen(experience = successState.selectedExperience,
                        onLikeClick = { viewModel.likeExperience(successState.selectedExperience.id) },
                        modifier = modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    searchBarState: SearchBarState,
    keyboardController: SoftwareKeyboardController?,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    scope: CoroutineScope,
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium)
            )
    ) {
        Icon(Icons.Default.Menu, contentDescription = null, modifier = Modifier.weight(1f))

        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            singleLine = true,
            placeholder = { Text(stringResource(R.string.try_luxor)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),


            leadingIcon = {
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },

            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = onCancelClick) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick()
                keyboardController?.hide()
            }),
            modifier = Modifier
                .weight(5f)
                .focusRequester(focusRequester)
                .clip(shape = MaterialTheme.shapes.small)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        scope.launch {
                            delay(300)
                            focusRequester.requestFocus()
                        }
                    }
                }

        )

        Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.weight(1f))
    }
}


@Composable
fun WelcomeText(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.displayLarge
            )
        Text(text = stringResource(R.string.welcome_body_text))
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(painter = painterResource(R.drawable.connection_error), contentDescription = "")
        Text(text = stringResource(R.string.oops), Modifier.padding(dimensionResource(R.dimen.padding_medium)))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(dimensionResource(R.dimen.loading_image_size)),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}



