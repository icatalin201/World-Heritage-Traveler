package com.app.worldheritagetraveler.tools

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import com.app.worldheritagetraveler.data.models.Language
import java.util.*

/**
World Heritage Traveler
Created by Catalin on 9/1/2020
 **/
object LanguageTool {

    const val LANGUAGE_VALUE = "language"

    fun refreshLanguage(context: Context) {
        val language = getLanguage(context)
        updateLocale(context, language)
    }

    fun getLanguage(context: Context): Language {
        val language = PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString(LANGUAGE_VALUE, Language.DEFAULT.name)
        return Language.valueOf(language!!)
    }

    fun updateLocale(context: Context, language: Language) {
        val locale = when (language) {
            Language.DEFAULT -> Locale.ROOT
            else -> Locale(language.name)
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }

}