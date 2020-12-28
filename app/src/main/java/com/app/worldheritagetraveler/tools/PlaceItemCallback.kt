package com.app.worldheritagetraveler.tools

import androidx.recyclerview.widget.DiffUtil
import com.app.worldheritagetraveler.data.models.Place

/**
World Heritage Traveler
Created by Catalin on 9/5/2020
 **/
object PlaceItemCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}