package com.example.aroundegyptmini.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "experiences")
@Serializable
data class ExperienceEntity(
    @PrimaryKey val id: String,
    val title: String,
    val coverPhoto: String,
    val description: String,
    val viewsNo: Int,
    val likesNo: Int,
    val recommended: Int,
    val address: String,
    val isLiked: Boolean?,
    val detailedDescription: String,
)


fun ExperienceEntity.toDomainModel(): Experience {
    return Experience(
        id = id,
        title = title,
        coverPhoto = coverPhoto,
        description = description,
        viewsNo = viewsNo,
        likesNo = likesNo,
        recommended = recommended,
        detailedDescription = detailedDescription,
        address = address,
        isLiked = isLiked
    )
}