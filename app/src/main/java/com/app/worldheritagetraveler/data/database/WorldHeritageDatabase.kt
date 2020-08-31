package com.app.worldheritagetraveler.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Place
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
@Database(entities = [Place::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorldHeritageDatabase : RoomDatabase() {

    abstract fun siteDao(): PlaceDao

    private class WorldHeritageCallback(
        private val context: Context
    ) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.i(TAG, "database created")
            val json = readFromJsonAsset(context, R.raw.data)
            val sites = Gson().fromJson(json, Array<Place>::class.java).toList()
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = database.siteDao()
                    sites.iterator().forEach { site -> dao.insert(site) }
                    Log.i(TAG, "data imported")
                }
            }
        }

        fun readFromJsonAsset(context: Context, name: Int): String {
            val inputStream = context.resources.openRawResource(name)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        }
    }

    companion object {

        private val TAG: String = WorldHeritageDatabase::class.java.name

        @Volatile
        private var INSTANCE: WorldHeritageDatabase? = null

        fun getInstance(context: Context): WorldHeritageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorldHeritageDatabase::class.java,
                    "world_heritage_database"
                ).addCallback(WorldHeritageCallback(context)).build()
                INSTANCE = instance
                instance
            }
        }
    }

}