package com.app.worldheritagetraveler.data.database

import androidx.room.TypeConverter
import com.app.worldheritagetraveler.data.models.PlaceLanguage
import com.google.gson.Gson

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
object Converters {

    private val gson = Gson()

    @JvmStatic
    @TypeConverter
    fun fromListOfPlaceLanguage(placeLanguageList: List<PlaceLanguage>): String {
        return gson.toJson(placeLanguageList)
    }

    @JvmStatic
    @TypeConverter
    fun toListOfPlaceLanguage(placeLanguageList: String): List<PlaceLanguage> {
        return gson.fromJson(placeLanguageList, Array<PlaceLanguage>::class.java).toList()
    }

}