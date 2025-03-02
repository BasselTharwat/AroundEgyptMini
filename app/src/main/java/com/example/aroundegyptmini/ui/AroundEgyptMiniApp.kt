@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.aroundegyptmini.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aroundegyptmini.ui.screens.HomeScreen
import com.example.aroundegyptmini.ui.screens.HomeScreenViewModel


@Composable
fun AroundEgyptMiniApp(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory)
        HomeScreen(
            homeScreenUiState = homeScreenViewModel.homeScreenUiState,
            retryAction = homeScreenViewModel::getExperiences
        )
    }
}


