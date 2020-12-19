package com.bmstu.iu9.swimrunners.androidrk1.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.bmstu.iu9.swimrunners.androidrk1.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        findPreference<SwitchPreference>(
            getString(R.string.theme_preference_key)
        )?.setOnPreferenceChangeListener { p, v ->
            onPreferenceChange(p, v)
        }
        findPreference<EditTextPreference>(
            getString(R.string.days_preference_key)
        )?.setOnPreferenceChangeListener { p, v ->
            onPreferenceChange(p, v)
        }
    }

    private fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if (preference!!.key == getString(R.string.days_preference_key)) {
            try {
                val days = newValue.toString().toInt()
                if (days <= 0) {
                    Toast.makeText(
                        context, "Too few days, enter bigger number", Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
            } catch (nfe: NumberFormatException) {
                Toast.makeText(
                    context, "Not a number, enter number", Toast.LENGTH_SHORT
                ).show()
                return false
            }
        } else if (preference.key == getString(R.string.theme_preference_key)) {
            Toast.makeText(
                context, "Reload the app to update the theme", Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}