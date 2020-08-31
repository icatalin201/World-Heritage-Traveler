package com.app.worldheritagetraveler.ui.place

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.ActivityPlaceBinding
import com.app.worldheritagetraveler.tools.Injection
import com.app.worldheritagetraveler.tools.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


/**
World Heritage Traveler
Created by catalin.matache on 8/29/2020
 */
class PlaceActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val PLACE_TITLE = "Place.Title"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mFactory: ViewModelFactory
    private lateinit var mBinding: ActivityPlaceBinding
    private lateinit var mPlaceTitle: String
    private var mIsFavorite = false
    private var mIsVisited = false
    private val mViewModel: PlaceViewModel by viewModels { mFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place)
        setCustomHeightForCover()
        setSupportActionBar(mBinding.placeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        mBinding.placeAppBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                mBinding.placeTitle.alpha =
                    (appBarLayout.totalScrollRange + verticalOffset) /
                            appBarLayout.totalScrollRange.toFloat()
            })
        title = ""
        mFactory = Injection.provideViewModelFactory(this)
        intent.getStringExtra(PLACE_TITLE)?.let { mPlaceTitle = it }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.place_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mViewModel.getByTitle(mPlaceTitle)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menu1 = when (mIsFavorite) {
            true -> R.menu.menu_favorite_on
            else -> R.menu.menu_favorite
        }
        val menu2 = when (mIsVisited) {
            true -> R.menu.menu_visited_on
            else -> R.menu.menu_visited
        }
        menuInflater.inflate(menu1, menu)
        menuInflater.inflate(menu2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            val message = when (mIsFavorite) {
                true -> R.string.place_removed_from_favorites
                false -> R.string.place_added_to_favorites
            }
            Snackbar.make(mBinding.placeCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
            mViewModel.toggleFavorite(mPlaceTitle)
        } else if (item.itemId == R.id.visited) {
            val message = when (mIsVisited) {
                true -> R.string.place_removed_from_visited
                false -> R.string.place_added_to_visited
            }
            Snackbar.make(mBinding.placeCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
            mViewModel.toggleVisited(mPlaceTitle)
        }
        invalidateOptionsMenu()
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.uiSettings.isZoomGesturesEnabled = false
        googleMap.uiSettings.isScrollGesturesEnabled = false
        this.mMap = googleMap
        mViewModel.place.observe(this, { place -> setupPlace(place) })
    }

    private fun setupPlace(place: Place) {
        Picasso.get().load(place.image)
            .centerCrop()
            .fit()
            .into(mBinding.placeImageCover)
        mBinding.place = place
        val latLng = LatLng(place.latitude, place.longitude)
        mMap.addMarker(MarkerOptions().position(latLng).title(place.title))
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 7.0f)
        mMap.animateCamera(cameraUpdate)
        mIsFavorite = place.favorite
        mIsVisited = place.visited
        invalidateOptionsMenu()
    }

    private fun setCustomHeightForCover() {
        val titleBarHeight = getStatusBarHeight()
        val params = mBinding.placeCoverLayout.layoutParams as CollapsingToolbarLayout.LayoutParams
        params.bottomMargin = -titleBarHeight
        mBinding.placeCoverLayout.layoutParams = params
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources
            .getIdentifier("status_bar_height", "dimen", "android")
        return when (resourceId > 0) {
            true -> resources.getDimensionPixelSize(resourceId)
            else -> 0
        }
    }
}