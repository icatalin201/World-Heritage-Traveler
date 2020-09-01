package com.app.worldheritagetraveler.ui.about

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.worldheritagetraveler.BuildConfig
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        title = getString(R.string.about)
        setSupportActionBar(mBinding.aboutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        mBinding.version = BuildConfig.VERSION_NAME
        mBinding.aboutSeePrivacyPolicy.setOnClickListener { openPrivacyPolicyDialog() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun openPrivacyPolicyDialog() {
        val webView = WebView(this)
        webView.loadUrl("file:///android_res/raw/privacy_policy.html")
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
            .setNeutralButton(R.string.close) { dialog, _ -> dialog.dismiss() }
            .setView(webView)
            .create()
            .show()
    }
}