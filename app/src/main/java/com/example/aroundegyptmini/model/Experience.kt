package com.example.aroundegyptmini.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@Serializable
data class ExperiencesResponse(
    val meta: Meta,
    val data: List<Experience> = emptyList(),
    val pagination: JsonObject
)


@Serializable
data class LikeResponse(
    val meta: Meta,
    val data: Int,
    val pagination: JsonObject
)

@Serializable
data class ExperienceResponse(
    val meta: Meta,
    val data: Experience,
    val pagination: JsonObject
)

@Serializable
data class Meta(
    val code: Int = 0,
    val errors: List<String> = emptyList()
)

@Serializable
data class Experience(
    val id: String = "",
    val title: String = "",
    @SerialName("cover_photo")
    val coverPhoto: String = "",
    val description: String = "",
    @SerialName("views_no")
    val viewsNo: Int = 0,
    @SerialName("likes_no")
    val likesNo: Int = 0,
    val recommended: Int = 0,
    @SerialName("detailed_description")
    val detailedDescription: String = "",
    val address: String = "",
    @SerialName("is_liked")
    val isLiked: Boolean? = false,
)

fun Experience.toEntity(): ExperienceEntity {
    return ExperienceEntity(
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

