package com.app.worldheritagetraveler.data.database

import androidx.room.TypeConverter
import com.app.worldheritagetraveler.data.models.PlaceType

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
object Converters {

    @JvmStatic
    @TypeConverter
    fun fromSiteType(placeType: PlaceType): String {
        return placeType.name
    }

    @JvmStatic
    @TypeConverter
    fun fromSiteTypeString(siteType: String): PlaceType {
        return PlaceType.valueOf(siteType)
    }

}