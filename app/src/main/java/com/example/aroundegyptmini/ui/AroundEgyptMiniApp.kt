@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.aroundegyptmini.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aroundegyptmini.ui.screens.HomeScreen
import com.example.aroundegyptmini.ui.screens.HomeScreenViewModel

@Composable
fun AroundEgyptMiniApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory)
        HomeScreen(
            viewModel = homeScreenViewModel,
            retryAction = homeScreenViewModel::getExperiences
        )
    }
}
