package com.app.worldheritagetraveler.ui.splash

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.PlaceRepository
import com.app.worldheritagetraveler.data.models.Place
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.get

/**
World Heritage Traveler
Created by Catalin on 9/2/2020
 **/
class PushDataWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(
    context,
    workerParams
) {

    override fun doWork(): Result {
        val repository: PlaceRepository = get(PlaceRepository::class.java)
        val json = readFromJsonAsset()
        val places = Gson().fromJson(json, Array<Place>::class.java)
        repository.insert(places)
        return Result.success()
    }

    private fun readFromJsonAsset(): String {
        val inputStream = context.resources.openRawResource(R.raw.data)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

}