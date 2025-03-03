package com.example.aroundegyptmini.network

import com.example.aroundegyptmini.model.Experience
import com.example.aroundegyptmini.model.ExperienceResponse
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import retrofit2.http.GET

interface ExperienceApiService{
    @GET("api/v2/experiences")
    suspend fun getRecentExperiences(): ExperienceResponse

    @GET("api/v2/experiences?filter[recommended]=true")
    suspend fun getRecommendedExperiences(): ExperienceResponse
}