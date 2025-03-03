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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeScreenUiState{
    data class Success(
        val recommended: List<Experience> = emptyList(),
        val recent: List<Experience> = emptyList(),
        val searchResults: List<Experience> = emptyList(),
        val selectedExperience: Experience? = null,
    ): HomeScreenUiState
    object Error: HomeScreenUiState
    object Loading: HomeScreenUiState
}

class HomeScreenViewModel(private val experienceRepository: ExperienceRepository): ViewModel(){

    private val _homeScreenUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()


    init {
        getExperiences()
    }

    fun getExperiences() {
        viewModelScope.launch {
            _homeScreenUiState.value = HomeScreenUiState.Loading
            try {
                val recommendedDeferred = async { experienceRepository.getRecommendedExperiences() }
                val recentDeferred = async { experienceRepository.getRecentExperiences() }

                val recommended = recommendedDeferred.await()
                val recent = recentDeferred.await()

                _homeScreenUiState.value = HomeScreenUiState.Success(recommended, recent)
            } catch (e: Exception) {
                _homeScreenUiState.value = HomeScreenUiState.Error

            }

        }

    }
    fun selectExperience(experience: Experience) {
        _homeScreenUiState.update { currentState ->
            if (currentState is HomeScreenUiState.Success) {
                currentState.copy(selectedExperience = experience)
            } else {
                currentState
            }
        }
    }

    fun deselectExperience() {
        _homeScreenUiState.update { currentState ->
            if (currentState is HomeScreenUiState.Success) {
                currentState.copy(selectedExperience = null)
            } else {
                currentState
            }
        }
    }

    fun searchExperiences(searchText: String){
        viewModelScope.launch {
            val recommendedBackup = (uiState.value as HomeScreenUiState.Success).recommended
            val recentBackup = (uiState.value as HomeScreenUiState.Success).recent
            _homeScreenUiState.value = HomeScreenUiState.Loading
            try {
                val resultsDeferred = async { experienceRepository.searchExperiences(searchText) }
                val results = resultsDeferred.await()

                if (results.isEmpty()) {
                    _homeScreenUiState.value = HomeScreenUiState.Error
                } else {
                    _homeScreenUiState.value = HomeScreenUiState.Success(
                        recommended = recommendedBackup,
                        recent = recentBackup,
                        searchResults = results
                    )
                }

            }catch (e: Exception) {
                    _homeScreenUiState.value = HomeScreenUiState.Error
                }
        }
    }

    fun clearSearchResults(){
        _homeScreenUiState.update { currentState ->
            if (currentState is HomeScreenUiState.Success) {
                currentState.copy(searchResults = emptyList())
            } else {
                currentState
            }
        }
    }

    fun likeExperience(id: Int) {
        viewModelScope.launch {
            try {
                val deferredExperience = async { experienceRepository.getExperience(id) }
                val experience = deferredExperience.await()
                val deferredUpdatedExperience = async { experienceRepository.likeExperience(id) }
                val updatedExperience = deferredUpdatedExperience.await()

                _homeScreenUiState.value = HomeScreenUiState.Success()
            } catch (e: Exception) {
                _homeScreenUiState.value = HomeScreenUiState.Error

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