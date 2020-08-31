package com.app.worldheritagetraveler.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.app.worldheritagetraveler.data.models.Place

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
@Dao
interface PlaceDao {

    @Query("select * from places")
    fun getAll(): LiveData<List<Place>>

    @Query("select * from places where title = :title")
    fun getByTitle(title: String): LiveData<Place>

    @RawQuery(observedEntities = [Place::class])
    fun getAll(query: SupportSQLiteQuery): LiveData<List<Place>>

    @Query(
        "select * from places where " +
                "latitude >= :latitude1 and " +
                "latitude <= :latitude2 and " +
                "longitude >= :longitude1 and " +
                "longitude <= :longitude2"
    )
    fun getAllByDistance(
        latitude1: Double,
        latitude2: Double,
        longitude1: Double,
        longitude2: Double
    ): LiveData<List<Place>>

    @Query("update places set favorite = NOT favorite where title = :title")
    suspend fun toggleFavorite(title: String)

    @Query("update places set visited = NOT visited where title = :title")
    suspend fun toggleVisited(title: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Place)

}