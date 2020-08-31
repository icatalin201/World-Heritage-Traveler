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
    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "short_description")
    val shortDescription: String,
    @ColumnInfo(name = "long_description")
    val longDescription: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "date_of_inscription")
    val dateOfInscription: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "site_type")
    val type: PlaceType,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean,
    @ColumnInfo(name = "visited")
    val visited: Boolean
)