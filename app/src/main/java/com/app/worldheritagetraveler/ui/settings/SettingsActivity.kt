package com.app.worldheritagetraveler.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.databinding.ActivitySettingsBinding
import com.app.worldheritagetraveler.tools.LanguageTool

/**
World Heritage Traveler
Created by Catalin on 9/1/2020
 **/
class SettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        setSupportActionBar(mBinding.settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        supportFragmentManager.beginTransaction()
            .add(R.id.settings_container_view, SettingsFragment())
            .commit()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        LanguageTool.refreshLanguage(this)
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}