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

    @SerialName("has_video")
    val hasVideo: Int = 0,

    val tags: List<Tag> = emptyList(),
    val city: City,

    val tours: List<String> = emptyList(),

    @SerialName("tour_url")
    val tourUrl: String = "",

    @SerialName("tour_xml")
    val tourXml: String = "",

    @SerialName("tour_html")
    val tourHtml: String = "",


    @SerialName("famous_figure")
    val famousFigure: String = "",

    val period: Period?,
    val era: Era?,
    val founded: String?,

    @SerialName("detailed_description")
    val detailedDescription: String = "",

    val address: String = "",

    @SerialName("gmap_location")
    val gmapLocation: GMapLocation,

    @SerialName("opening_hours")
    val openingHours: Map<String, List<String>> = emptyMap(),

    @SerialName("translated_opening_hours")
    val translatedOpeningHours: Map<String, TranslatedOpeningHour> = emptyMap(),

    @SerialName("starting_price")
    val startingPrice: Int? = 0,

    @SerialName("ticket_prices")
    val ticketPrices: List<TicketPrice> = emptyList(),

    @SerialName("experience_tips")
    val experienceTips: List<String> = emptyList(),

    @SerialName("is_liked")
    val isLiked: Boolean? = false,

    val reviews: List<Review> = emptyList(),

    val rating: Int = 0,

    @SerialName("reviews_no")
    val reviewsNo: Int = 0,

    @SerialName("audio_url")
    val audioUrl: String? = "",

    @SerialName("has_audio")
    val hasAudio: Boolean = false,
)

@Serializable
data class Tag(
    val id: Int = 0,
    val name: String = "",
    val disable: String? = "",
    @SerialName("top_pick")
    val topPick: Int = 0
)

@Serializable
data class City(
    val id: Int = 0,
    val name: String = "",
    val disable: String? = "",
    @SerialName("top_pick")
    val topPick: Int = 0
)

@Serializable
data class Era(
    val id: String = "",
    val value: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("updated_at")
    val updatedAt: String = ""
)

@Serializable
data class GMapLocation(
    val type: String = "",
    val coordinates: List<Double> = emptyList()
)

@Serializable
data class TranslatedOpeningHour(
    val day: String = "",
    val time: String = ""
)

@Serializable
data class TicketPrice(
    val type: String = "",
    val price: Int = 0
)

@Serializable
data class Review(
    val id: String = "",
    val experience: String = "",
    val name: String = "",
    val rating: Int = 0,
    val comment: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
)

@Serializable
data class Period(
    val id: String = "",
    val value: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("updated_at")
    val updatedAt: String = ""
)

fun City.toEntity(): CityEntity {
    return CityEntity(
        id = id,
        name = name,
        disable = disable,
        topPick = topPick
    )
}
fun Era.toEntity(): EraEntity {
    return EraEntity(
        id = id,
        value = value,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun Period.toEntity(): PeriodEntity {
    return PeriodEntity(
        id = id,
        value = value,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun GMapLocation.toEntity(): GMapLocationEntity {
    return GMapLocationEntity(
        type = type,
        coordinates = coordinates.joinToString(",")
    )
}

fun TranslatedOpeningHour.toEntity(): TranslatedOpeningHourEntity {
    return TranslatedOpeningHourEntity(
        day = day,
        time = time
    )
}

fun TicketPrice.toEntity(): TicketPriceEntity {
    return TicketPriceEntity(
        type = type,
        price = price
    )
}

fun Review.toEntity(): ReviewEntity {
    return ReviewEntity(
        id = id,
        experience = experience,
        name = name,
        rating = rating,
        comment = comment,
        createdAt = createdAt
    )
}

fun Tag.toEntity(): TagEntity {
    return TagEntity(
        id = id,
        name = name,
        disable = disable,
        topPick = topPick,
    )
}



fun Experience.toEntity(): ExperienceEntity {
    return ExperienceEntity(
        id = id,
        title = title,
        coverPhoto = coverPhoto,
        description = description,
        viewsNo = viewsNo,
        likesNo = likesNo,
        recommended = recommended,
        hasVideo = hasVideo,
        address = address,
        rating = rating,
        reviewsNo = reviewsNo,
        audioUrl = audioUrl,
        hasAudio = hasAudio,
        isLiked = isLiked,

        tags = Json.encodeToString(tags),
        ticketPrices = Json.encodeToString(ticketPrices),
        reviews = Json.encodeToString(reviews),

        famousFigure = famousFigure,
        detailedDescription = detailedDescription,
        gmapLocation = gmapLocation.toEntity(),
        startingPrice = startingPrice,
        tourUrl = tourUrl,
        tourXml = tourXml,
        tourHtml = tourHtml,
        founded = founded,
        city = city.toEntity(),
        era = era?.toEntity(),
        period = period?.toEntity(),
        tours = Json.encodeToString(tours),
        openingHours = Json.encodeToString(openingHours),
        translatedOpeningHours = Json.encodeToString(translatedOpeningHours),
        experienceTips = Json.encodeToString(experienceTips),
    )
}