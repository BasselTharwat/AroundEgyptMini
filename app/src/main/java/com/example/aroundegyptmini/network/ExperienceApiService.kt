package com.example.aroundegyptmini.network

import com.example.aroundegyptmini.model.Experience
import retrofit2.http.GET

interface ExperienceApiService{
    @GET("api/v2/experiences")
    suspend fun getRecentExperiences(): List<Experience>

    @GET("api/v2/experiences?filter[recommended]=true")
    suspend fun getRecommendedExperiences(): List<Experience>
}