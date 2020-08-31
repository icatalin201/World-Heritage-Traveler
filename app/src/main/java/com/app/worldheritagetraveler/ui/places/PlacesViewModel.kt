package com.app.worldheritagetraveler.ui.places

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.models.FilterOptions
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.data.models.SortOptions
import com.app.worldheritagetraveler.tools.Factorizable

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class PlacesViewModel(private val placeRepository: PlaceRepository) : ViewModel(), Factorizable {

    val mPlaceList: MediatorLiveData<List<Place>> = MediatorLiveData()
    var mFilterOptions = FilterOptions(favorite = false, visited = false)
    private var mSortOptions = SortOptions.DEFAULT

    init {
        triggerQuery()
    }

    fun applyFilter(filterOptions: FilterOptions) {
        this.mFilterOptions = filterOptions
        triggerQuery()
    }

    fun applySort(sortOptions: SortOptions) {
        this.mSortOptions = sortOptions
        triggerQuery()
    }

    private fun triggerQuery() {
        mPlaceList.addSource(placeRepository.getAll(mFilterOptions, mSortOptions)) { places ->
            mPlaceList.value = places
        }
    }
}