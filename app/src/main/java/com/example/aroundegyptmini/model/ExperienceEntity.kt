package com.example.aroundegyptmini.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import kotlinx.serialization.SerialName
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
    val hasVideo: Int,
    val address: String,
    val rating: Int,
    val reviewsNo: Int,
    val audioUrl: String?,
    val hasAudio: Boolean,
    val isLiked: Boolean?,
    val famousFigure: String,
    val detailedDescription: String,
    val gmapLocation: GMapLocation,
    val startingPrice: Int?,
    val tourUrl: String,
    val tourXml: String,
    val tourHtml: String,
    val founded: String?,



    @Embedded val city: CityEntity?,
    @Embedded val era: EraEntity?,
    @Embedded val period: PeriodEntity?,


    val tags: String,
    val ticketPrices: String,
    val reviews: String,
    val tours: String,
    val openingHours: String,
    val translatedOpeningHours: String,
    val experienceTips: String,
)

@Serializable
data class TagEntity(
    val id: Int = 0,
    val name: String = "",
    val disable: String? = "",
    val topPick: Int = 0
)

@Serializable
data class CityEntity(
    val id: Int = 0,
    val name: String = "",
    val disable: String? = "",
    @SerialName("top_pick")
    val topPick: Int = 0
)

@Serializable
data class EraEntity(
    val id: String = "",
    val value: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

@Serializable
data class GMapLocationEntity(
    val type: String = "",
    val coordinates: String = ""
)

@Serializable
data class TranslatedOpeningHourEntity(
    val day: String = "",
    val time: String = ""
)

@Serializable
data class TicketPriceEntity(
    val type: String = "",
    val price: Int = 0
)

@Serializable
data class ReviewEntity(
    val id: String = "",
    val experience: String = "",
    val name: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val createdAt: String = "",
)

@Serializable
data class PeriodEntity(
    val id: String = "",
    val value: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)


