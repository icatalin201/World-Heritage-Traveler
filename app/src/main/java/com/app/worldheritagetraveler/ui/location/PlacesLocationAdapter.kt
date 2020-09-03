package com.app.worldheritagetraveler.ui.location

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Language
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.ItemLocationPlaceRecyclerBinding
import com.app.worldheritagetraveler.tools.LanguageTool
import com.squareup.picasso.Picasso

/**
World Heritage Traveler
Created by Catalin on 8/30/2020
 **/
class PlacesLocationAdapter(
    private val listener: PlaceLocationListener,
    private val context: Context
) :
    RecyclerView.Adapter<PlacesLocationAdapter.PlacesViewHolder>() {

    inner class PlacesViewHolder(private val binding: ItemLocationPlaceRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun render(position: Int) {
            val place = mPlaceList[position]
            binding.place = place
            var language = LanguageTool.getLanguage(context)
            if (language == Language.DEFAULT) {
                language = Language.EN
            }
            binding.placeLanguage = place.findPlaceLanguage(language)
            binding.listener = listener
            val layoutParams = binding.itemPlaceCard.layoutParams as RecyclerView.LayoutParams
            val isTablet = context.resources.getBoolean(R.bool.is_tablet)
            if (position == 0 || (position == 1 && isTablet)) {
                layoutParams.topMargin = applyDimension()
            } else {
                layoutParams.topMargin = 0
            }
            if (isTablet && position % 2 == 0) {
                layoutParams.marginEnd = 0
            } else {
                layoutParams.marginEnd = applyDimension()
            }
            binding.itemPlaceCard.layoutParams = layoutParams
            Picasso.get().load(place.image)
                .centerCrop()
                .fit()
                .into(binding.itemPlaceImage)
            val favoriteResource = when (place.favorite) {
                true -> R.drawable.ic_baseline_favorite_24
                false -> R.drawable.ic_baseline_favorite_border_24
            }
            val visitedResource = when (place.visited) {
                true -> R.drawable.ic_baseline_visibility_24
                false -> R.drawable.ic_baseline_visibility_off_24
            }
            binding.itemPlaceFavorite.setImageResource(favoriteResource)
            binding.itemPlaceVisited.setImageResource(visitedResource)
        }

        private fun applyDimension(): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8F,
                context.resources.displayMetrics
            ).toInt()
        }

    }

    private var mPlaceList = emptyList<Place>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding: ItemLocationPlaceRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_location_place_recycler,
            parent,
            false
        )
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.render(position)
    }

    override fun getItemCount(): Int {
        return mPlaceList.size
    }

    internal fun setPlaces(places: List<Place>) {
        this.mPlaceList = places
        notifyDataSetChanged()
    }

}