package com.app.worldheritagetraveler.ui.splash

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import org.koin.core.component.KoinApiExtension

/**
World Heritage Traveler
Created by Catalin on 9/2/2020
 **/
class SplashViewModel(
    context: Context
) : ViewModel() {

    companion object {
        private const val IS_FIRST_TIME = "First.Time.Run"
        private const val IMPORT_DATA_TAG = "Import.Data.Tag"
    }

    val isFirstTime = MediatorLiveData<Boolean>()
    private val workManager = WorkManager.getInstance(context)
    private val mPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        isFirstTime.value = mPreferences.getBoolean(IS_FIRST_TIME, true)
    }

    fun startImporting() {
        isFirstTime.addSource(
            workManager.getWorkInfosByTagLiveData(IMPORT_DATA_TAG)
        ) { workInfoList ->
            if (workInfoList.size > 0) {
                val workInfo: WorkInfo = workInfoList[0]
                val finished = workInfo.state.isFinished
                if (finished) {
                    mPreferences.edit().putBoolean(IS_FIRST_TIME, false).apply()
                    isFirstTime.value = false
                }
            }
        }
        val request = OneTimeWorkRequest
            .Builder(PushDataWorker::class.java)
            .addTag(IMPORT_DATA_TAG)
            .build()
        workManager.enqueue(request)
    }

}