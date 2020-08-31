package com.app.worldheritagetraveler.tools

import android.content.Context
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.database.PlaceDao
import com.app.worldheritagetraveler.data.database.WorldHeritageDatabase
import com.app.worldheritagetraveler.ui.location.LocationViewModel
import com.app.worldheritagetraveler.ui.map.MapViewModel
import com.app.worldheritagetraveler.ui.place.PlaceViewModel
import com.app.worldheritagetraveler.ui.places.PlacesViewModel

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
object Injection {

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val viewModels = provideViewModelsList(context)
        return ViewModelFactory(viewModels)
    }

    private fun providePlaceDao(context: Context): PlaceDao {
        val database = WorldHeritageDatabase.getInstance(context)
        return database.siteDao()
    }

    private fun providePlaceRepository(context: Context): PlaceRepository {
        val dao = providePlaceDao(context)
        return PlaceRepository.getInstance(dao)
    }

    private fun provideViewModelsList(context: Context): List<Factorizable> {
        val repo = providePlaceRepository(context)
        return listOf(
            PlacesViewModel(repo),
            LocationViewModel(repo),
            MapViewModel(repo),
            PlaceViewModel(repo)
        )
    }

}