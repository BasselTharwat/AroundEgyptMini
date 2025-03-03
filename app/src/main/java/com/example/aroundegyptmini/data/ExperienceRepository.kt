package com.example.aroundegyptmini.data

import com.example.aroundegyptmini.model.Experience
import com.example.aroundegyptmini.network.ExperienceApiService

interface ExperienceRepository{
    suspend fun getRecentExperiences(): List<Experience>
    suspend fun getRecommendedExperiences(): List<Experience>
    suspend fun searchExperiences(searchText: String): List<Experience>
    suspend fun getExperience(id: Int): Experience
    suspend fun likeExperience(id: Int) : Experience

}

class NetworkExperienceRepository(
    private val experienceApiService: ExperienceApiService
): ExperienceRepository{
    override suspend fun getRecentExperiences(): List<Experience> = experienceApiService.getRecentExperiences().data
    override suspend fun getRecommendedExperiences(): List<Experience> = experienceApiService.getRecommendedExperiences().data
    override suspend fun searchExperiences(searchText: String): List<Experience> = experienceApiService.searchExperiences(searchText).data
    override suspend fun getExperience(id: Int): Experience = experienceApiService.getExperience(id).data
    override suspend fun likeExperience(id: Int): Experience = experienceApiService.likeExperience(id)


}