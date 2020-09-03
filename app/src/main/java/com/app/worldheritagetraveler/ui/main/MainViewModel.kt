package com.app.worldheritagetraveler.ui.main

import androidx.lifecycle.ViewModel
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.tools.Factorizable

/**
World Heritage Traveler
Created by Catalin on 9/2/2020
 **/
class MainViewModel(placeRepository: PlaceRepository) : ViewModel(), Factorizable {

    val place = placeRepository.getRandom()

}