package com.app.worldheritagetraveler.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Language
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.databinding.ActivityPlaceBinding
import com.app.worldheritagetraveler.tools.LanguageTool
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
import org.koin.android.ext.android.inject


/**
World Heritage Traveler
Created by catalin.matache on 8/29/2020
 */
class PlaceActivity : AppCompatActivity(), OnMapReadyCallback, CountryPlaceListener {

    companion object {
        const val PLACE = "Place"
        const val COUNTRY = "Country"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mBinding: ActivityPlaceBinding
    private lateinit var mAdapter: CountryPlacesAdapter
    private var mPlaceId: Int = 0
    private var mIsFavorite = false
    private var mIsVisited = false
    private val mViewModel: PlaceViewModel by inject()

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
        mAdapter = CountryPlacesAdapter(this, this)
        mBinding.placeRecyclerView.adapter = mAdapter
        mBinding.placeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mPlaceId = intent.getIntExtra(PLACE, 0)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.place_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mViewModel.getById(mPlaceId, intent.getStringExtra(COUNTRY))
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
            mViewModel.toggleFavorite(mPlaceId)
        } else if (item.itemId == R.id.visited) {
            val message = when (mIsVisited) {
                true -> R.string.place_removed_from_visited
                false -> R.string.place_added_to_visited
            }
            Snackbar.make(mBinding.placeCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
            mViewModel.toggleVisited(mPlaceId)
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
        mViewModel.countryPlaces.observe(this, { mAdapter.submitList(it) })
    }

    override fun open(place: Place) {
        val intent = Intent(this, PlaceActivity::class.java)
        intent.putExtra(PLACE, place.id)
        intent.putExtra(COUNTRY, place.country)
        startActivity(intent)
    }

    private fun setupPlace(place: Place) {
        Picasso.get().load(place.image)
            .centerCrop()
            .fit()
            .into(mBinding.placeImageCover)
        mBinding.place = place
        var language = LanguageTool.getLanguage(this)
        if (language == Language.DEFAULT) {
            language = Language.EN
        }
        mBinding.placeLanguage = place.findPlaceLanguage(language)
        val latLng = LatLng(place.latitude, place.longitude)
        mMap.addMarker(
            MarkerOptions().position(latLng).title(place.findPlaceLanguage(Language.EN).title)
        )
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