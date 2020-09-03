package com.app.worldheritagetraveler.ui.place

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.tools.Factorizable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
class PlaceViewModel(private val placeRepository: PlaceRepository) : ViewModel(), Factorizable {

    val place: MediatorLiveData<Place> = MediatorLiveData()

    fun getById(id: Int) {
        place.addSource(placeRepository.getById(id)) { this.place.value = it }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.toggleFavorite(id)
        }
    }

    fun toggleVisited(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.toggleVisited(id)
        }
    }

}