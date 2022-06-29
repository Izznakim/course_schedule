package com.dicoding.courseschedule.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var THEME: String
    private lateinit var NOTIFICATION: String

    private lateinit var themePreference: ListPreference
    private lateinit var notificationPreference: SwitchPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference [SOLVED]
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference [SOLVED]
        init()
    }

    private fun init() {
        THEME = resources.getString(R.string.pref_key_dark)
        NOTIFICATION = resources.getString(R.string.pref_key_notify)

        themePreference = findPreference<ListPreference>(THEME) as ListPreference
        notificationPreference = findPreference<SwitchPreference>(NOTIFICATION) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == THEME) {
            val mode = NightMode.valueOf(themePreference.value.toUpperCase())
            updateTheme(mode.value)
        }

        if (key == NOTIFICATION) {
            val dailyReminder=DailyReminder()
            if (notificationPreference.isChecked){
                dailyReminder.setDailyReminder(requireContext())
            }else{
                dailyReminder.cancelAlarm(requireContext())
            }
        }
    }
}

