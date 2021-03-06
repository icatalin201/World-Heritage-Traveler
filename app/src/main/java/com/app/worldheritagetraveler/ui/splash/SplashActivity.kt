package com.app.worldheritagetraveler.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.databinding.ActivitySplashBinding
import com.app.worldheritagetraveler.ui.main.MainActivity
import org.koin.android.ext.android.inject

/**
World Heritage Traveler
Created by Catalin on 9/2/2020
 **/
class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding
    private val mViewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mViewModel.isFirstTime.observe(this, { isFirstTime ->
            if (isFirstTime) {
                mBinding.splashProgressBar.visibility = View.VISIBLE
                mViewModel.startImporting()
            } else {
                mBinding.splashProgressBar.visibility = View.GONE
                startApp()
            }
        })
    }

    private fun startApp() {
        val start = Intent(this, MainActivity::class.java)
        startActivity(start)
        finish()
    }
}