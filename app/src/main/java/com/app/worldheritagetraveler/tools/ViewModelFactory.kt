package com.app.worldheritagetraveler.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
World Heritage Traveler
Created by Catalin on 8/28/2020
 **/
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val mViewModels: List<Factorizable>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var instance: Factorizable? = null
        mViewModels.listIterator().forEach { viewModel ->
            if (modelClass.isAssignableFrom(viewModel::class.java)) {
                instance = viewModel
                return@forEach
            }
        }
        if (instance == null) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return instance as T
    }

}