package com.example.aroundegyptmini.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aroundegyptmini.AroundEgyptMiniApplication
import com.example.aroundegyptmini.data.ExperienceRepository
import com.example.aroundegyptmini.model.Experience
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeScreenUiState{
    data class Success(
        val recommended: List<Experience>,
        val recent: List<Experience>,
        val searchResults: List<Experience> = emptyList()
    ): HomeScreenUiState
    data class Error(val message: String): HomeScreenUiState
    object Loading: HomeScreenUiState
}

class HomeScreenViewModel(private val experienceRepository: ExperienceRepository): ViewModel(){
    var homeScreenUiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.Loading)

    init {
        getExperiences()
    }

    fun getExperiences() {
        viewModelScope.launch {
            homeScreenUiState = HomeScreenUiState.Loading
            try {
                val recommendedDeferred = async { experienceRepository.getRecommendedExperiences() }
                val recentDeferred = async { experienceRepository.getRecentExperiences() }

                val recommended = recommendedDeferred.await()
                val recent = recentDeferred.await()

                homeScreenUiState = HomeScreenUiState.Success(recommended, recent)
            } catch (e: IOException) {
                homeScreenUiState = HomeScreenUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                homeScreenUiState = HomeScreenUiState.Error(e.message.toString())
            } catch (e: RuntimeException) {
                homeScreenUiState = HomeScreenUiState.Error(e.message.toString())
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AroundEgyptMiniApplication)
                val experienceRepository = application.container.experienceRepository
                HomeScreenViewModel(experienceRepository = experienceRepository)
            }
        }
    }

}