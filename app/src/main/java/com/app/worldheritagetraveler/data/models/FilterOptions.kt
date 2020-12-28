package com.app.worldheritagetraveler.data.models

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
data class FilterOptions(
    var favorite: Boolean,
    var visited: Boolean,
    var country: String
) {
    fun getValue(): String {
        val builder = StringBuilder()
        builder.append("where ")
        builder.append(
            when (favorite) {
                true -> "favorite = 1"
                false -> "favorite in (0, 1)"
            }
        )
        builder.append(" and ")
        builder.append(
            when (visited) {
                true -> "visited = 1"
                false -> "visited in (0, 1)"
            }
        )
        builder.append(
            when (country) {
                "-Country-" -> ""
                else -> " and country LIKE '%$country%'"
            }
        )
        return builder.toString()
    }
}