package com.app.worldheritagetraveler.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
World Heritage Traveler
Created by Catalin on 9/2/2020
 **/
class PlaceMarker(
    private val mPosition: LatLng,
    private val mTitle: String,
    private val mSnippet: String,
    private val mId: Int
) : ClusterItem {

    override fun getPosition(): LatLng {
        return mPosition
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSnippet(): String {
        return mSnippet
    }

    fun getId(): Int {
        return mId
    }

}