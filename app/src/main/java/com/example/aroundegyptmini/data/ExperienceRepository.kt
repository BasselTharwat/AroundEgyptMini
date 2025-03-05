package com.example.aroundegyptmini.data

import com.example.aroundegyptmini.model.Experience
import com.example.aroundegyptmini.model.LikeResponse
import com.example.aroundegyptmini.model.toDomainModel
import com.example.aroundegyptmini.model.toEntity
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
) : ExperienceRepository {

    override suspend fun getRecentExperiences(): List<Experience> {
        val localExperiences = experienceDao.getAllExperiences().map { it.toDomainModel() }
        return if (localExperiences.isNotEmpty()) {
            //Log.d("NetworkExperienceRepository", "Returning local experiences")
            localExperiences
        } else {
            val remoteExperiences = experienceApiService.getRecentExperiences().data
            experienceDao.insertAllExperiences(remoteExperiences.map { it.toEntity() })
            //Log.d("NetworkExperienceRepository", "Returning remote experiences")
            remoteExperiences
        }
    }

    override suspend fun getRecommendedExperiences(): List<Experience> {
        val localExperiences = experienceDao.getRecommendedExperiences().map { it.toDomainModel() }
        return if (localExperiences.isNotEmpty()) {
            //Log.d("NetworkExperienceRepository", "Returning local recommended experiences")
            localExperiences
        } else {
            val remoteExperiences = experienceApiService.getRecommendedExperiences().data
            experienceDao.insertAllExperiences(remoteExperiences.map { it.toEntity() })
            //Log.d("NetworkExperienceRepository", "Returning remote recommended experiences")
            remoteExperiences
        }
    }

    override suspend fun searchExperiences(searchText: String): List<Experience> {
        val localResults = experienceDao.searchExperiences(searchText).map { it.toDomainModel() }
        return if (localResults.isNotEmpty()) {
            //Log.d("NetworkExperienceRepository", "Returning local search results")
            localResults
        } else {
            val remoteResults = experienceApiService.searchExperiences(searchText).data
            experienceDao.insertAllExperiences(remoteResults.map { it.toEntity() })
            //Log.d("NetworkExperienceRepository", "Returning remote search results")
            remoteResults
        }
    }

    override suspend fun getExperience(id: String): Experience {
        val localExperience = experienceDao.getExperienceById(id)?.toDomainModel()
        return if (localExperience != null) {
            //Log.d("NetworkExperienceRepository", "Returning local experience")
            localExperience
        } else {
            val remoteExperience = experienceApiService.getExperience(id).data
            //Log.d("NetworkExperienceRepository", "Returning remote experience")
            remoteExperience
        }
    }

    override suspend fun likeExperience(id: String): LikeResponse {
        val response = experienceApiService.likeExperience(id)

        experienceDao.likeExperience(id)

        //Log.d("NetworkExperienceRepository", "Experience liked: $id")
        return response
    }
}
