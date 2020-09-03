package com.app.worldheritagetraveler.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.worldheritagetraveler.data.models.Place

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
@Database(entities = [Place::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorldHeritageDatabase : RoomDatabase() {

    abstract fun siteDao(): PlaceDao

    companion object {

        @Volatile
        private var INSTANCE: WorldHeritageDatabase? = null

        fun getInstance(context: Context): WorldHeritageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorldHeritageDatabase::class.java,
                    "world_heritage_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}