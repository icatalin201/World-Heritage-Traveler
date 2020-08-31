package com.app.worldheritagetraveler.data

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.app.worldheritagetraveler.data.database.PlaceDao
import com.app.worldheritagetraveler.data.models.FilterOptions
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.data.models.SortOptions

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class PlaceRepository(private val placeDao: PlaceDao) {

    companion object {
        @Volatile
        private var INSTANCE: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao): PlaceRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = PlaceRepository(placeDao)
                INSTANCE = instance
                instance
            }
        }
    }

    val places: LiveData<List<Place>> = placeDao.getAll()

    suspend fun toggleFavorite(place: Place) {
        toggleFavorite(place.title)
    }

    suspend fun toggleVisited(place: Place) {
        toggleVisited(place.title)
    }

    suspend fun toggleFavorite(title: String) {
        placeDao.toggleFavorite(title)
    }

    suspend fun toggleVisited(title: String) {
        placeDao.toggleVisited(title)
    }

    fun getByTitle(title: String): LiveData<Place> {
        return placeDao.getByTitle(title)
    }

    fun getAll(filterOptions: FilterOptions, sortOptions: SortOptions): LiveData<List<Place>> {
        val queryBuilder = StringBuilder()
        queryBuilder.append("select * from places")
        queryBuilder.append(" ")
        queryBuilder.append(filterOptions.getValue())
        queryBuilder.append(" ")
        queryBuilder.append(sortOptions.getValue())
        val query = SimpleSQLiteQuery(queryBuilder.toString())
        return placeDao.getAll(query)
    }

    fun getAllByDistance(
        latitude1: Double,
        latitude2: Double,
        longitude1: Double,
        longitude2: Double
    ): LiveData<List<Place>> {
        return placeDao.getAllByDistance(latitude1, latitude2, longitude1, longitude2)
    }
}
