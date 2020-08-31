package com.app.worldheritagetraveler.data.models

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
data class FilterOptions(
    var favorite: Boolean,
    var visited: Boolean
) {
    fun getValue(): String {
        return if (favorite && !visited) {
            "where favorite = 1"
        } else if (!favorite && visited) {
            "where visited = 1"
        } else if (favorite && visited) {
            "where favorite = 1 and visited = 1"
        } else {
            ""
        }
    }
}