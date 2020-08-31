package com.app.worldheritagetraveler.ui.places

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.ItemPlaceRecyclerBinding
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
            val layoutParams = binding.itemPlaceCard.layoutParams as RecyclerView.LayoutParams
            if (position == 0) {
                layoutParams.topMargin =
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8F,
                        context.resources.displayMetrics
                    ).toInt()
            } else {
                layoutParams.topMargin = 0
            }
            binding.itemPlaceCard.layoutParams = layoutParams
            Picasso.get().load(place.image)
                .centerCrop()
                .fit()
                .into(binding.itemPlaceImage)
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

    internal fun setSites(places: List<Place>) {
        this.mPlaceList = places
        notifyDataSetChanged()
    }

}