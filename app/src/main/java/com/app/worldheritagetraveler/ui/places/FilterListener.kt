package com.app.worldheritagetraveler.ui.places

import com.app.worldheritagetraveler.data.models.FilterOptions

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
interface FilterListener {
    fun onApply(filterOptions: FilterOptions)
    fun onCancel()
}