package com.example.aroundegyptmini.model

import kotlinx.serialization.Serializable

@Serializable
data class Experience(
    val id: String,
    val title: String,
    val coverPhoto: String,
    val description: String,
    val viewsNo: Int,
    val likesNo: Int,
    val recommended: Int,
    val hasVideo: Int,
    val tags: List<Tag>,
    val city: City,
    val tourHtml: String?,
    val famousFigure: String?,
    val period: String?,
    val era: Era?,
    val founded: String?,
    val detailedDescription: String,
    val address: String,
    val gmapLocation: GMapLocation,
    val openingHours: Map<String, List<String>>,
    val translatedOpeningHours: Map<String, TranslatedOpeningHour>,
    val startingPrice: Int,
    val ticketPrices: List<TicketPrice>,
    val experienceTips: List<String>,
    val isLiked: Boolean?,
    val reviews: List<String>,
    val rating: Int,
    val reviewsNo: Int,
    val audioUrl: String?,
    val hasAudio: Boolean
)

@Serializable
data class Tag(
    val id: Int,
    val name: String,
    val disable: String?,
    val topPick: Int
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    val disable: String?,
    val topPick: Int
)

@Serializable
data class Era(
    val id: String,
    val value: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class GMapLocation(
    val type: String,
    val coordinates: List<Double>
)

@Serializable
data class TranslatedOpeningHour(
    val day: String,
    val time: String
)

@Serializable
data class TicketPrice(
    val type: String,
    val price: Int
)
