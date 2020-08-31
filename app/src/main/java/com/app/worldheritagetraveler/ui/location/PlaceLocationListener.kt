package com.app.worldheritagetraveler.ui.location

import com.app.worldheritagetraveler.data.models.Place

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
interface PlaceLocationListener {
    fun toggleFavorite(place: Place)
    fun toggleVisited(place: Place)
    fun open(place: Place)
}