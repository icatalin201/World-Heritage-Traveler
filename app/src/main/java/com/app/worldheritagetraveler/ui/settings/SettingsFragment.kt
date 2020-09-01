package com.app.worldheritagetraveler.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.Language
import com.app.worldheritagetraveler.tools.LanguageTool
import com.app.worldheritagetraveler.tools.LanguageTool.LANGUAGE_VALUE

/**
World Heritage Traveler
Created by Catalin on 9/1/2020
 **/
class SettingsFragment : PreferenceFragmentCompat() {

    private val sBindPreferenceSummaryToValueListener =
        Preference.OnPreferenceChangeListener { preference: Preference, value: Any ->
            val stringValue = value.toString()
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(stringValue)
                if (index >= 0) {
                    val entry = preference.entries[index]
                    val entryValue = preference.entryValues[index]
                    preference.setSummary(entry)
                    if (preference.getKey() == LANGUAGE_VALUE) {
                        val language = LanguageTool.getLanguage(requireContext())
                        val newLanguage = Language.valueOf(entryValue.toString())
                        if (language != newLanguage) {
                            LanguageTool.updateLocale(
                                requireContext(),
                                Language.valueOf(entryValue.toString())
                            )
                            requireActivity().recreate()
                        }
                    }
                }
            } else {
                preference.summary = stringValue
            }
            true
        }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val languagePreference = findPreference<Preference>(LANGUAGE_VALUE)
        bindPreferenceSummaryToValue(languagePreference)
    }

    private fun bindPreferenceSummaryToValue(preference: Preference?) {
        preference!!.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener
        sBindPreferenceSummaryToValueListener
            .onPreferenceChange(
                preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, Language.DEFAULT.name)
            )
    }
}