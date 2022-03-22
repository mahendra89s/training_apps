package com.example.fcm_training.following

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.fcm_training.R
import com.google.firebase.messaging.FirebaseMessaging

class FollowingPreferenceFragment : PreferenceFragmentCompat() {
    private var preferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Add visualizer preferences, defined in the XML file in res->xml->preferences_squawker
        addPreferencesFromResource(R.xml.following_squawker)
        preferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                Toast.makeText(context,key,Toast.LENGTH_LONG).show()
                val preference = findPreference<Preference>(key)
                when (preference) {
                    is SwitchPreferenceCompat -> {
                        when(key){
                            "key_asser" -> {
                                if(preference.isChecked){
                                    FirebaseMessaging.getInstance().subscribeToTopic("key_asser")
                                    Toast.makeText(context,"Checked",Toast.LENGTH_LONG).show()
                                }
                                else{
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("key_asser")
                                    Toast.makeText(context,"Unchecked",Toast.LENGTH_LONG).show()
                                }
                            }
                            "key_cezanne" -> {
                                if(preference.isChecked){
                                    FirebaseMessaging.getInstance().subscribeToTopic("key_cezanne")
                                }
                                else{
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("key_cezanne")
                                }
                            }
                            "key_jlin" -> {
                                if(preference.isChecked){
                                    FirebaseMessaging.getInstance().subscribeToTopic("key_jlin")
                                }
                                else{
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("key_jlin")
                                }
                            }
                            "key_lyla" -> {
                                if(preference.isChecked){
                                    FirebaseMessaging.getInstance().subscribeToTopic("key_lyla")
                                }
                                else{
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("key_lyla")
                                }
                            }
                            "key_nikita" -> {
                                if(preference.isChecked){
                                    FirebaseMessaging.getInstance().subscribeToTopic("key_nikita")
                                }
                                else{
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("key_nikita")
                                }
                            }


                        }

                    }
                }
            }
    }
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    companion object {
        private val LOG_TAG = FollowingPreferenceFragment::class.java.simpleName
    }
}