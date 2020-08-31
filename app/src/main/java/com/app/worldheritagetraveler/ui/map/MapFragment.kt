package com.app.worldheritagetraveler.ui.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.FragmentMapBinding
import com.app.worldheritagetraveler.tools.Injection
import com.app.worldheritagetraveler.tools.ViewModelFactory
import com.app.worldheritagetraveler.ui.place.PlaceActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mFactory: ViewModelFactory
    private lateinit var mBinding: FragmentMapBinding
    private val mViewModel: MapViewModel by viewModels { mFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map,
            container, false
        )
        mFactory = Injection.provideViewModelFactory(requireContext())
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return mBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnInfoWindowClickListener(this)
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mViewModel.placesList.observe(
            viewLifecycleOwner,
            { placesList -> setupMarkers(googleMap, placesList) })
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onInfoWindowClick(marker: Marker) {
        val intent = Intent(requireContext(), PlaceActivity::class.java)
        intent.putExtra(PlaceActivity.PLACE_TITLE, marker.title)
        startActivity(intent)
    }

    private fun setupMarkers(googleMap: GoogleMap, placesList: List<Place>) {
        placesList.listIterator().forEach { place ->
            val marker = MarkerOptions()
                .position(LatLng(place.latitude, place.longitude))
                .title(place.title)
            googleMap.addMarker(marker)
        }

    }

}