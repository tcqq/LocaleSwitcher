package com.tcqq.localeswitcher

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.text.TextUtils
import java.util.*

/**
 * Locale switcher.
 *
 *
 * Created by Alan Dreamer on 04/10/18.
 */
class LocaleSwitcherHelper(private val context: Context) {

    private var displayName: String = ""
    private var language: String = ""
    private var country: String = ""
    private var position: Int = 0
    private var deviceLanguage: Boolean = true
    private var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun configureBaseContext(): Context {
        return updateResources(Locale(getLanguage(), getCountry()))
    }

    private fun updateResources(locale: Locale): Context {
        var newBase = context
        Locale.setDefault(locale)

        val res = newBase.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        newBase = newBase.createConfigurationContext(config)
        return newBase
    }

    fun getDisplayName(): String {
        return prefs.getString(DISPLAY_NAME, "")
    }

    fun getPosition(): Int {
        return prefs.getInt(POSITION, 0)
    }

    private fun getLanguage(): String {
        return prefs.getString(LANGUAGE, "")
    }

    private fun getCountry(): String {
        return prefs.getString(COUNTRY, "")
    }

    fun isDeviceLanguage(): Boolean {
        return prefs.getBoolean(DEVICE_LANGUAGE, true)
    }

    fun setDisplayName(name: String): LocaleSwitcherHelper {
        displayName = name
        return this
    }

    fun setPosition(position: Int): LocaleSwitcherHelper {
        this.position = position
        return this
    }

    fun setLocale(language: String, country: String?): LocaleSwitcherHelper {
        this.language = language
        this.country = country.toString()
        return this
    }

    fun setDeviceLanguage(deviceLanguage: Boolean): LocaleSwitcherHelper {
        this.deviceLanguage = deviceLanguage
        return this
    }

    fun apply() {
        prefs.edit()
                .putInt(POSITION, position)
                .putBoolean(DEVICE_LANGUAGE, deviceLanguage).apply()

        if (!TextUtils.isEmpty(displayName)) {
            prefs.edit().putString(DISPLAY_NAME, displayName).apply()
        }

        if (!TextUtils.isEmpty(language)) {
            prefs.edit()
                    .putString(LANGUAGE, language)
                    .putString(COUNTRY, country).apply()
            updateResources(Locale(language, country))
        }
    }

    companion object {
        fun newInstance(context: Context): LocaleSwitcherHelper {
            return LocaleSwitcherHelper(context)
        }

        internal const val PREF_NAME = "locale_switcher"
        internal const val DISPLAY_NAME = "display_name"
        internal const val POSITION = "position"
        internal const val DEVICE_LANGUAGE = "device_language"
        internal const val LANGUAGE = "language"
        internal const val COUNTRY = "country"
    }
}
