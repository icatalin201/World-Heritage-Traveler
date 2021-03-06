package com.app.worldheritagetraveler.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.databinding.ActivityMainBinding
import com.app.worldheritagetraveler.tools.LanguageTool
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_header_navigation.view.*
import org.koin.android.ext.android.inject

/**
World Heritage Traveler
Created by catalin.matache on 8/27/2020
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mNavController: NavController
    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageTool.refreshLanguage(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding.mainToolbar)
        setupNavigation()
        mViewModel.place.observe(
            this,
            { place ->
                val header = mBinding.mainSideNavigationView.getHeaderView(0)
                Picasso.get()
                    .load(place.image)
                    .centerCrop()
                    .fit()
                    .into(header.cover_header_image)
            })
    }

    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.activity_about,
            R.id.activity_settings,
            R.id.fragment_map,
            R.id.fragment_location,
            R.id.fragment_places
        ).setOpenableLayout(mBinding.mainDrawerLayout).build()
        mNavController = Navigation.findNavController(this, R.id.main_navigation_host)
        NavigationUI.setupWithNavController(mBinding.mainBottomNavigationView, mNavController)
        NavigationUI.setupWithNavController(mBinding.mainSideNavigationView, mNavController)
        NavigationUI.setupWithNavController(
            mBinding.mainToolbar,
            mNavController,
            appBarConfiguration
        )
    }
}