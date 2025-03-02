package com.example.aroundegyptmini.data

import com.example.aroundegyptmini.model.Experience
import com.example.aroundegyptmini.network.ExperienceApiService

interface ExperienceRepository{
    suspend fun getRecentExperiences(): List<Experience>
    suspend fun getRecommendedExperiences(): List<Experience>

}

class NetworkExperienceRepository(
    private val experienceApiService: ExperienceApiService
): ExperienceRepository{
    override suspend fun getRecentExperiences(): List<Experience> = experienceApiService.getRecentExperiences()
    override suspend fun getRecommendedExperiences(): List<Experience> = experienceApiService.getRecommendedExperiences()

}