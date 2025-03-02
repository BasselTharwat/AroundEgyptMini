package com.example.aroundegyptmini.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.aroundegyptmini.AroundEgyptMiniApplication
import com.example.aroundegyptmini.data.ExperienceRepository
import com.example.aroundegyptmini.model.Experience
import kotlinx.coroutines.launch
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

    fun getExperiences(){
        viewModelScope.launch {
            homeScreenUiState = HomeScreenUiState.Loading
            var recommended: List<Experience> = emptyList()
            var recent: List<Experience> = emptyList()
            homeScreenUiState = try {
                launch {
                    recommended = experienceRepository.getRecommendedExperiences()
                }
                launch {
                    recent = experienceRepository.getRecentExperiences()
                }
                HomeScreenUiState.Success(recommended, recent)
            }catch (e: IOException){
                HomeScreenUiState.Error(e.message.toString())
            }catch (e: HttpException){
                HomeScreenUiState.Error(e.message.toString())
            }catch (e: RuntimeException){
                HomeScreenUiState.Error(e.message.toString())
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AroundEgyptMiniApplication)
                val experienceRepository = application.container.experienceRepository
                HomeScreenViewModel(experienceRepository = experienceRepository)
            }
        }
    }

}