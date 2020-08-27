package com.app.worldheritagetraveler.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

/**
World Heritage Traveler
Created by catalin.matache on 8/27/2020
 */
open class BaseActivity : AppCompatActivity() {

    private lateinit var unbinder: Unbinder

    override fun setContentView(view: View?) {
        super.setContentView(view)
        unbinder = ButterKnife.bind(this);
    }

    override fun onDestroy() {
        unbinder.unbind()
        super.onDestroy()
    }

}