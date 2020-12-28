package com.app.worldheritagetraveler.ui.place

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Language
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.ItemOtherPlaceRecyclerBinding
import com.app.worldheritagetraveler.tools.LanguageTool
import com.app.worldheritagetraveler.tools.PlaceItemCallback
import com.squareup.picasso.Picasso

/**
World Heritage Traveler
Created by Catalin on 9/5/2020
 **/
class CountryPlacesAdapter(
    private val listener: CountryPlaceListener,
    private val context: Context
) : ListAdapter<Place, CountryPlacesAdapter.PlacesViewHolder>(PlaceItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding: ItemOtherPlaceRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_other_place_recycler,
            parent,
            false
        )
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.render(position)
    }

    inner class PlacesViewHolder(private val binding: ItemOtherPlaceRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun render(position: Int) {
            val place = getItem(position)
            binding.place = place
            var language = LanguageTool.getLanguage(context)
            if (language == Language.DEFAULT) {
                language = Language.EN
            }
            binding.placeLanguage = place.findPlaceLanguage(language)
            binding.listener = listener
            val layoutParams = binding.itemPlaceCard.layoutParams as RecyclerView.LayoutParams
            if (position == 0) {
                layoutParams.marginStart = applyDimension()
            } else {
                layoutParams.marginStart = 0
            }
            binding.itemPlaceCard.layoutParams = layoutParams
            Picasso.get().load(place.image)
                .centerCrop()
                .fit()
                .into(binding.itemPlaceImage)
        }

        private fun applyDimension(): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8F,
                context.resources.displayMetrics
            ).toInt()
        }

    }
}