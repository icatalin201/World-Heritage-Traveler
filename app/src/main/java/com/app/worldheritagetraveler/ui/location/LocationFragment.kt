package com.app.worldheritagetraveler.ui.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.FragmentLocationBinding
import com.app.worldheritagetraveler.tools.Injection
import com.app.worldheritagetraveler.tools.ViewModelFactory
import com.app.worldheritagetraveler.ui.place.PlaceActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class LocationFragment : Fragment(), PlaceLocationListener {

    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 11
    }

    private lateinit var mFactory: ViewModelFactory
    private lateinit var mAdapter: PlacesLocationAdapter
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mBinding: FragmentLocationBinding
    private val mViewModel: LocationViewModel by viewModels { mFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_location,
            container, false
        )
        setHasOptionsMenu(true)
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        mFactory = Injection.provideViewModelFactory(requireContext())
        mAdapter = PlacesLocationAdapter(this, requireContext())
        mBinding.locationRecyclerView.adapter = mAdapter
        mBinding.locationRecyclerView.adapter = mAdapter
        val isTablet = requireContext().resources.getBoolean(R.bool.is_tablet)
        if (isTablet) {
            mBinding.locationRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            mBinding.locationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        setupLocationListener()
        mViewModel.placeList.observe(viewLifecycleOwner, { mAdapter.setPlaces(it) })
        return mBinding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            setupLocationListener()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_location, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.distance) {
            val alertDialog = AlertDialog
                .Builder(requireContext(), R.style.AppTheme_Dialog)
                .setTitle(R.string.distance)
                .setItems(
                    R.array.distance_options
                ) { dialog, position ->
                    mViewModel.applyMaxDistance(mViewModel.distances[position])
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun toggleFavorite(place: Place) {
        mViewModel.toggleFavorite(place)
        val message = when (place.favorite) {
            true -> R.string.place_removed_from_favorites
            false -> R.string.place_added_to_favorites
        }
        Snackbar.make(mBinding.locationCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun toggleVisited(place: Place) {
        mViewModel.toggleVisited(place)
        val message = when (place.visited) {
            true -> R.string.place_removed_from_visited
            false -> R.string.place_added_to_visited
        }
        Snackbar.make(mBinding.locationCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun open(place: Place) {
        val intent = Intent(requireContext(), PlaceActivity::class.java)
        intent.putExtra(PlaceActivity.PLACE, place.id)
        startActivity(intent)
    }

    private fun setupLocationListener() {
        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_LOCATION_PERMISSION_CODE
            )
        }
        mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { lastLocation ->
                val latitude = lastLocation.latitude
                val longitude = lastLocation.longitude
                mViewModel.myPosition = LatLng(latitude, longitude)
                mViewModel.applyMaxDistance(distance = 500)
            }
        }
    }

}