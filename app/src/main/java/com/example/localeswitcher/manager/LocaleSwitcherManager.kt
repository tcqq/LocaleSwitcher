package com.example.localeswitcher.manager

import android.content.Context
import android.content.res.Configuration
import com.example.localeswitcher.pref.LanguageSettingsPref
import java.util.*

/**
 * Locale switcher manager.
 *
 * @author Alan Dreamer
 * @since 04/10/2018 Created
 */
object LocaleSwitcherManager {

    fun configureBaseContext(newBase: Context): Context {
        return updateResources(newBase, Locale(LanguageSettingsPref.language, LanguageSettingsPref.country))
    }

    private fun updateResources(newBase: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val res = newBase.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        return newBase.createConfigurationContext(config)
    }
}
