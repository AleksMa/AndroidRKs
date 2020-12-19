package com.bmstu.iu9.swimrunners.androidrk1.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bmstu.iu9.swimrunners.androidrk1.R

class SettingsActivity : AppCompatActivity(), ThemeCustomizable {
    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme(PreferenceManager.getDefaultSharedPreferences(application))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun applyTheme(prefs: SharedPreferences) {
        if (prefs.getBoolean(getString(R.string.theme_preference_key), false)) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}
