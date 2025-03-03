package com.example.aroundegyptmini.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.ui.components.RecentExperiences
import com.example.aroundegyptmini.ui.components.RecommendedExperiences
import com.example.aroundegyptmini.ui.components.SearchExperiences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val homeScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchResultsShow: Boolean by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()

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
                .padding(dimensionResource(R.dimen.padding_small))) {
                TopBar(
                    searchBarState = searchBarState,
                    textFieldState = textFieldState,
                    scope = scope,
                    onCancelClick = {
                        if(searchResultsShow){
                            viewModel.clearSearchResults()
                            textFieldState.clearText()
                            searchResultsShow = false
                            scope.launch { searchBarState.animateToCollapsed() }
                        }else{
                            scope.launch { searchBarState.animateToCollapsed() }
                        }

                    },
                    onSearchClick = {
                        if(textFieldState.text.isNotEmpty()){
                        viewModel.searchExperiences(textFieldState.text.toString())
                        searchResultsShow = true }
                    }

                )
                Spacer(modifier.padding(dimensionResource(R.dimen.padding_medium)))
                if (!searchResultsShow){
                    WelcomeText()
                    Spacer(modifier.padding(dimensionResource(R.dimen.padding_medium)))
                    RecommendedExperiences(
                        recommendedExperiences = successState.recommended,
                        onExperienceClick = { experience ->
                            viewModel.selectExperience(experience)
                            scope.launch { sheetState.show() }
                        }
                    )
                    Spacer(modifier.padding(dimensionResource(R.dimen.padding_medium)))
                    RecentExperiences(
                        recentExperiences = successState.recent,
                        onExperienceClick = { experience ->
                            viewModel.selectExperience(experience)
                            scope.launch { sheetState.show() }
                        }
                    )
                }else{
                    SearchExperiences(
                        searchExperiences = successState.searchResults,
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
    textFieldState: TextFieldState,
    scope: CoroutineScope,
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit
){

    val inputField =
        @Composable {
            SearchBarDefaults.
            InputField(modifier = Modifier,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = {  if (textFieldState.text.isEmpty())
                    Text(stringResource(R.string.try_luxor))  },
                leadingIcon = { IconButton(
                    onClick = onSearchClick
                ){
                    Icon(Icons.Default.Search, contentDescription = null)
                } },
                trailingIcon = {
                    if (searchBarState.currentValue == SearchBarValue.Expanded) {
                        IconButton(
                            onClick = onCancelClick
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            )
        }

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium)
            )
    ){
        Icon(Icons.Default.Menu,
            contentDescription = null,
            modifier = modifier
                .weight(1f))

        SearchBar(
            state = searchBarState,
            inputField = inputField,
            modifier = modifier
                .weight(5f))

        Icon(Icons.Default.Tune,
            contentDescription = null,
            modifier = modifier
                .weight(1f)
        )

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



