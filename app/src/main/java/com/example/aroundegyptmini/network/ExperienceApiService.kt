package com.example.aroundegyptmini.network

import com.example.aroundegyptmini.model.ExperienceResponse
import com.example.aroundegyptmini.model.ExperiencesResponse
import com.example.aroundegyptmini.model.LikeResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExperienceApiService{
    @GET("api/v2/experiences")
    suspend fun getRecentExperiences(): ExperiencesResponse

    @GET("api/v2/experiences")
    suspend fun getRecommendedExperiences(
        @Query("filter[recommended]") recommended: Boolean = true
    ): ExperiencesResponse

    @GET("/api/v2/experiences")
    suspend fun searchExperiences(
        @Query("filter[title]") searchText: String
    ): ExperiencesResponse

    @GET("/api/v2/experiences/{id}")
    suspend fun getExperience(@Path("id") id: String): ExperienceResponse


    @POST("/api/v2/experiences/{id}/like")
    suspend fun likeExperience(@Path("id") id: String) : LikeResponse


}