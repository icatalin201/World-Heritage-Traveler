package com.app.worldheritagetraveler.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.tools.Factorizable

/**
World Heritage Traveler
Created by Catalin on 8/29/2020
 **/
class MapViewModel(placeRepository: PlaceRepository) : ViewModel(), Factorizable {

    val placesList: LiveData<List<Place>> = placeRepository.places

}