package com.app.worldheritagetraveler.ui.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Language
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.data.models.PlaceMarker
import com.app.worldheritagetraveler.databinding.FragmentMapBinding
import com.app.worldheritagetraveler.tools.Injection
import com.app.worldheritagetraveler.tools.LanguageTool
import com.app.worldheritagetraveler.tools.ViewModelFactory
import com.app.worldheritagetraveler.ui.place.PlaceActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager


/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mFactory: ViewModelFactory
    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mClusterManager: ClusterManager<PlaceMarker>
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
        mClusterManager = ClusterManager(requireContext(), googleMap)
        googleMap.setOnInfoWindowClickListener(mClusterManager)
        googleMap.setOnCameraIdleListener(mClusterManager)
        googleMap.setOnMarkerClickListener(mClusterManager)
        mClusterManager.setOnClusterItemInfoWindowClickListener { onInfoWindowClick(it) }
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mViewModel.placesList.observe(
            viewLifecycleOwner,
            { placesList -> setupMarkers(placesList) })
    }

    private fun onInfoWindowClick(placeMarker: PlaceMarker) {
        val intent = Intent(requireContext(), PlaceActivity::class.java)
        intent.putExtra(PlaceActivity.PLACE, placeMarker.getId())
        startActivity(intent)
    }

    private fun setupMarkers(placesList: List<Place>) {
        placesList.forEach {
            var language = LanguageTool.getLanguage(requireContext())
            if (language == Language.DEFAULT) {
                language = Language.EN
            }
            val title = it.findPlaceLanguage(language).title
            val placeMarker = PlaceMarker(
                LatLng(it.latitude, it.longitude),
                title,
                it.country,
                it.id
            )
            mClusterManager.addItem(placeMarker)
        }
        mClusterManager.cluster()
    }

}