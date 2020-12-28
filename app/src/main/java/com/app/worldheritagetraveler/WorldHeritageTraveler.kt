package com.app.worldheritagetraveler

import android.app.Application
import com.app.worldheritagetraveler.tools.KoinModule
import com.app.worldheritagetraveler.tools.OkHttpTool.ignoreAllSSLErrors
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class WorldHeritageTraveler : Application() {

    override fun onCreate() {
        super.onCreate()
        setupPicasso()
        startKoin {
            androidLogger()
            androidContext(this@WorldHeritageTraveler)
            modules(KoinModule.appModule)
        }
    }

    private fun setupPicasso() {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .ignoreAllSSLErrors().build()
        val okHttpDownloader = OkHttp3Downloader(okHttpClient)
        val picasso = Picasso.Builder(this)
            .downloader(okHttpDownloader)
            .build()
        Picasso.setSingletonInstance(picasso)
    }

}