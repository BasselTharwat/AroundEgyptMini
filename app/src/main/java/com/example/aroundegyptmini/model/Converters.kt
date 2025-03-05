package com.example.aroundegyptmini.model

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

object Converters {
    @TypeConverter
    fun fromTagList(value: List<Tag>): String = Json.encodeToString(value)

    @TypeConverter
    fun toTagList(value: String): List<Tag> = Json.decodeFromString(value)

    @TypeConverter
    fun fromTicketPriceList(value: List<TicketPrice>): String = Json.encodeToString(value)

    @TypeConverter
    fun toTicketPriceList(value: String): List<TicketPrice> = Json.decodeFromString(value)

    @TypeConverter
    fun fromReviewList(value: List<Review>): String = Json.encodeToString(value)

    @TypeConverter
    fun toReviewList(value: String): List<Review> = Json.decodeFromString(value)

    @TypeConverter
    fun fromTourList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toTourList(value: String): List<String> = Json.decodeFromString(value)

    @TypeConverter
    fun fromOpeningHoursMap(value: Map<String, List<String>>): String = Json.encodeToString(value)

    @TypeConverter
    fun toOpeningHoursMap(value: String): Map<String, List<String>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromTranslatedOpeningHoursMap(value: Map<String, TranslatedOpeningHour>): String = Json.encodeToString(value)

    @TypeConverter
    fun toTranslatedOpeningHoursMap(value: String): Map<String, TranslatedOpeningHour> = Json.decodeFromString(value)

    @TypeConverter
    fun fromExperienceTipList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toExperienceTipList(value: String): List<String> = Json.decodeFromString(value)

    @TypeConverter
    fun fromCoordinates(value: List<Double>): String = Json.encodeToString(value)

    @TypeConverter
    fun toCoordinates(value: String): List<Double> = Json.decodeFromString(value)

}