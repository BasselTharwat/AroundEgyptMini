package com.example.aroundegyptmini.data

import android.content.Context
import com.example.aroundegyptmini.network.ExperienceApiService
import com.example.aroundegyptmini.network.ExperienceDao
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val experienceRepository: ExperienceRepository
}

class DefaultAppContainer(context: Context) : AppContainer{

    private val database: ExperienceDatabase by lazy {
        ExperienceDatabase.getDatabase(context)
    }

    private val experienceDao: ExperienceDao = database.experienceDao()


    private val baseUrl = "https://aroundegypt.34ml.com/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ExperienceApiService by lazy {
        retrofit.create(ExperienceApiService::class.java)
    }

    override val experienceRepository: ExperienceRepository by lazy {
        NetworkExperienceRepository(retrofitService, experienceDao)
    }
}