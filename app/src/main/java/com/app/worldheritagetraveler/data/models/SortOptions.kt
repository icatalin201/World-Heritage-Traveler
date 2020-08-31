package com.app.worldheritagetraveler.data.models

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
enum class SortOptions {
    DEFAULT,
    TITLE,
    COUNTRY;

    fun getValue(): String {
        return when (this) {
            TITLE, COUNTRY -> "order by %s asc".format(this.name)
            DEFAULT -> ""
        }
    }

    companion object {
        fun ofPosition(position: Int): SortOptions {
            return when (position) {
                0 -> DEFAULT
                1 -> TITLE
                2 -> COUNTRY
                else -> DEFAULT
            }
        }
    }
}