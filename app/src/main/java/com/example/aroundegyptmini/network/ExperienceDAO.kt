package com.example.aroundegyptmini.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aroundegyptmini.model.ExperienceEntity

@Dao
interface ExperienceDao {
    @Query("SELECT * FROM experiences")
    suspend fun getAllExperiences(): List<ExperienceEntity>

    @Query("SELECT * FROM experiences WHERE recommended = 1")
    suspend fun getRecommendedExperiences(): List<ExperienceEntity>

    @Query("SELECT * FROM experiences WHERE id = :id")
    suspend fun getExperienceById(id: String): ExperienceEntity

    @Query("SELECT * FROM experiences WHERE title LIKE '%' || :searchText || '%'")
    suspend fun searchExperiences(searchText: String): List<ExperienceEntity>

    @Query("UPDATE experiences SET likesNo = likesNo + 1 WHERE id = :id")
    suspend fun likeExperience(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExperiences(experiences: List<ExperienceEntity>)

}