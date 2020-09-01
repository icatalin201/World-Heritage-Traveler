package com.app.worldheritagetraveler.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean,
    @ColumnInfo(name = "visited")
    val visited: Boolean,
    @ColumnInfo(name = "language_content")
    val languageContent: List<PlaceLanguage>
) {
    fun findPlaceLanguage(language: Language): PlaceLanguage? {
        return languageContent.firstOrNull { placeLanguage ->
            placeLanguage.language == language.name
        }
    }
}