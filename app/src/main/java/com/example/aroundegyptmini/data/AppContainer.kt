package com.example.aroundegyptmini.data

import com.example.aroundegyptmini.network.ExperienceApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer{
    val experienceRepository: ExperienceRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://aroundegypt.34ml.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ExperienceApiService by lazy {
        retrofit.create(ExperienceApiService::class.java)
    }

    override val experienceRepository: ExperienceRepository by lazy {
        NetworkExperienceRepository(retrofitService)
    }
}