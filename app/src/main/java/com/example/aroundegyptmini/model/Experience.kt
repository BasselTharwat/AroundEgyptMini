package com.example.aroundegyptmini.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ExperienceResponse(
    val meta: Meta,
    val data: List<Experience>,
    val pagination: JsonObject
)

@Serializable
data class Meta(
    val code: Int,
    val errors: List<String>
)

@Serializable
data class Experience(
    val id: String,
    val title: String,

    @SerialName("cover_photo")
    val coverPhoto: String,

    val description: String,

    @SerialName("views_no")
    val viewsNo: Int,

    @SerialName("likes_no")
    val likesNo: Int,

    val recommended: Int,

    @SerialName("has_video")
    val hasVideo: Int,

    val tags: List<Tag>,
    val city: City,

    @SerialName("tour_html")
    val tourHtml: String?,

    @SerialName("famous_figure")
    val famousFigure: String?,

    val period: Period?,
    val era: Era?,
    val founded: String?,

    @SerialName("detailed_description")
    val detailedDescription: String,

    val address: String,

    @SerialName("gmap_location")
    val gmapLocation: GMapLocation,

    @SerialName("opening_hours")
    val openingHours: Map<String, List<String>>,

    @SerialName("translated_opening_hours")
    val translatedOpeningHours: Map<String, TranslatedOpeningHour>,

    @SerialName("starting_price")
    val startingPrice: Int?,

    @SerialName("ticket_prices")
    val ticketPrices: List<TicketPrice>,

    @SerialName("experience_tips")
    val experienceTips: List<String>,

    @SerialName("is_liked")
    val isLiked: Boolean?,

    val reviews: List<Review>,

    val rating: Int,

    @SerialName("reviews_no")
    val reviewsNo: Int,

    @SerialName("audio_url")
    val audioUrl: String?,

    @SerialName("has_audio")
    val hasAudio: Boolean
)

@Serializable
data class Tag(
    val id: Int,
    val name: String,
    val disable: String?,
    @SerialName("top_pick")
    val topPick: Int
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    val disable: String?,
    @SerialName("top_pick")
    val topPick: Int
)

@Serializable
data class Era(
    val id: String,
    val value: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
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

@Serializable
data class Review(
    val id: String,
    val experience: String,
    val name: String,
    val rating: Int,
    val comment: String,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class Period(
    val id: String,
    val value: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)