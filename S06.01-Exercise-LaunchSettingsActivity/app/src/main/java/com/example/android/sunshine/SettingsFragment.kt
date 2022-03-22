package com.example.android.sunshine

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener{
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general,rootKey)
        val sharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen = preferenceScreen
        val count = prefScreen.preferenceCount
        for( i in 0 until count){
            val p = prefScreen.getPreference(i)
            if(p !is CheckBoxPreference){
                val value = sharedPreferences.getString(p.key,"")
                setSummaryPreference(p,value!!)
            }
        }
    }

    private fun setSummaryPreference(preference: Preference,value : String){
        val stringValue = value.toString()
        val key = preference.key
        if(preference is ListPreference){
            val listPreference : ListPreference = preference
            val prefIndex = listPreference.findIndexOfValue(stringValue)
            if(prefIndex >= 0){
                preference.setSummary(listPreference.entries[prefIndex])
            }
        }
        else{
            preference.summary = stringValue
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        val preference : Preference? = findPreference(p1!!)
        if(preference != null){
            if(preference !is CheckBoxPreference){
                setSummaryPreference(preference,p0?.getString(p1,"")!!)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

}