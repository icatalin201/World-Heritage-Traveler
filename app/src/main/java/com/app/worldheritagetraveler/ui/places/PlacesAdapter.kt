package com.app.worldheritagetraveler.ui.places

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Language
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.ItemPlaceRecyclerBinding
import com.app.worldheritagetraveler.tools.LanguageTool
import com.squareup.picasso.Picasso

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class PlacesAdapter(
    private val listener: PlaceActionListener,
    private val context: Context
) :
    RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    inner class PlacesViewHolder(private val binding: ItemPlaceRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun render(position: Int) {
            val place = mPlaceList[position]
            binding.place = place
            binding.listener = listener
            var language = LanguageTool.getLanguage(context)
            if (language == Language.DEFAULT) {
                language = Language.EN
            }
            binding.placeLanguage = place.findPlaceLanguage(language)
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
        val binding: ItemPlaceRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_place_recycler,
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