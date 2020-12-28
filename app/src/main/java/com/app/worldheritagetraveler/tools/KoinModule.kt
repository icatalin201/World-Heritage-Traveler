package com.app.worldheritagetraveler.tools

import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.database.WorldHeritageDatabase
import com.app.worldheritagetraveler.ui.location.LocationViewModel
import com.app.worldheritagetraveler.ui.main.MainViewModel
import com.app.worldheritagetraveler.ui.map.MapViewModel
import com.app.worldheritagetraveler.ui.place.PlaceViewModel
import com.app.worldheritagetraveler.ui.places.PlacesViewModel
import com.app.worldheritagetraveler.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
World Heritage Traveler
Created by Catalin on 10/30/2020
 **/
object KoinModule {

    val appModule = module {

        single { WorldHeritageDatabase.getInstance(androidApplication()) }
        single { get<WorldHeritageDatabase>().placeDao() }
        single { PlaceRepository(get()) }

        viewModel { PlacesViewModel(get()) }
        viewModel { LocationViewModel(get()) }
        viewModel { MapViewModel(get()) }
        viewModel { PlaceViewModel(get()) }
        viewModel { MainViewModel(get()) }
        viewModel { SplashViewModel(get()) }

    }

}