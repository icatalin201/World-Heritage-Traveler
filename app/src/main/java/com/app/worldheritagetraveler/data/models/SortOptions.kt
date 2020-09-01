package com.app.worldheritagetraveler.data.models

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
enum class SortOptions {
    DEFAULT,
    YEAR,
    COUNTRY;

    fun getValue(): String {
        return when (this) {
            YEAR, COUNTRY -> "order by %s asc".format(this.name)
            DEFAULT -> "order by id asc"
        }
    }

    companion object {
        fun ofPosition(position: Int): SortOptions {
            return when (position) {
                0 -> DEFAULT
                1 -> YEAR
                2 -> COUNTRY
                else -> DEFAULT
            }
        }
    }
}