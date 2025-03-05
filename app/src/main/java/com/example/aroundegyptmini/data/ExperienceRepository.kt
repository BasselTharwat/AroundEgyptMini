package com.example.aroundegyptmini.data

import com.example.aroundegyptmini.model.Experience
import com.example.aroundegyptmini.model.LikeResponse
import com.example.aroundegyptmini.network.ExperienceApiService
import com.example.aroundegyptmini.network.ExperienceDao

interface ExperienceRepository{
    suspend fun getRecentExperiences(): List<Experience>
    suspend fun getRecommendedExperiences(): List<Experience>
    suspend fun searchExperiences(searchText: String): List<Experience>
    suspend fun getExperience(id: String): Experience
    suspend fun likeExperience(id: String) : LikeResponse

}

class NetworkExperienceRepository(
    private val experienceApiService: ExperienceApiService,
    private val experienceDao: ExperienceDao
): ExperienceRepository{
    override suspend fun getRecentExperiences(): List<Experience> {
        val localExperiences = experienceDao.getAllExperiences()
        if (localExperiences.isEmpty()) {
            val remoteExperiences = experienceApiService.getRecentExperiences().data
            experienceDao.insertAllExperiences(remoteExperiences)
            return remoteExperiences
        }
    }
    override suspend fun getRecommendedExperiences(): List<Experience> = experienceApiService.getRecommendedExperiences().data
    override suspend fun searchExperiences(searchText: String): List<Experience> = experienceApiService.searchExperiences(searchText).data
    override suspend fun getExperience(id: String): Experience = experienceApiService.getExperience(id).data
    override suspend fun likeExperience(id: String) : LikeResponse = experienceApiService.likeExperience(id)


}