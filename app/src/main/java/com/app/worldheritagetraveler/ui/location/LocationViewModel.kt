package com.app.worldheritagetraveler.ui.location

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.models.Place
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
World Heritage Traveler
Created by Catalin on 8/28/2020
 **/
class LocationViewModel(private val placeRepository: PlaceRepository) : ViewModel() {

    val distances = arrayOf(100, 500, 1000)
    val placeList: MediatorLiveData<List<Place>> = MediatorLiveData()
    var myPosition: LatLng? = null

    fun toggleFavorite(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.toggleFavorite(place)
        }
    }

    fun toggleVisited(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.toggleVisited(place)
        }
    }

    fun applyMaxDistance(distance: Int) {
        if (myPosition == null) return
        val overflow: Double = when (distance) {
            100 -> 0.9009
            500 -> 4.5045
            1000 -> 9.0090
            else -> 0.00
        }
        val latitude1 = myPosition!!.latitude - overflow
        val longitude1 = myPosition!!.longitude - overflow
        val latitude2 = myPosition!!.latitude + overflow
        val longitude2 = myPosition!!.longitude + overflow
        placeList.addSource(
            placeRepository.getAllByDistance(
                latitude1,
                latitude2,
                longitude1,
                longitude2
            )
        ) {
            placeList.value =
                it.sortedBy { place ->
                    getKilometersFromCoordinates(place)
                }
        }
    }

    private fun getKilometersFromCoordinates(place: Place): Double {
        var x = (myPosition!!.latitude - place.latitude) * 110.574
        if (x < 0) {
            x = x * -1
        }
        return x
    }
}