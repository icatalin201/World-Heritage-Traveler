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

    val places: LiveData<List<Place>> = placeDao.getAll()

    fun insert(placeList: Array<Place>) {
        placeDao.insert(placeList)
    }

    suspend fun toggleFavorite(place: Place) {
        toggleFavorite(place.id)
    }

    suspend fun toggleVisited(place: Place) {
        toggleVisited(place.id)
    }

    suspend fun toggleFavorite(id: Int) {
        placeDao.toggleFavorite(id)
    }

    suspend fun toggleVisited(id: Int) {
        placeDao.toggleVisited(id)
    }

    suspend fun getAllCountries(): List<String> {
        return placeDao.getAllCountries()
    }

    fun getAllByCountry(country: String): LiveData<List<Place>> {
        return placeDao.getAllByCountry("%$country%")
    }

    fun getById(id: Int): LiveData<Place> {
        return placeDao.getById(id)
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

    fun getRandom(): LiveData<Place> {
        return placeDao.getRandom()
    }
}
