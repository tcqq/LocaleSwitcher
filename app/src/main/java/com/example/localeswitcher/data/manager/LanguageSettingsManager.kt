package com.example.localeswitcher.data.manager

import android.content.Context
import com.example.localeswitcher.R
import com.example.localeswitcher.data.models.LanguageSettingsModel
import com.tcqq.localeswitcher.LocaleSwitcherHelper

/**
 * Language settings.
 *
 * @author Alan Dreamer
 * @since 04/27/2018 Created
 */
object LanguageSettingsManager {

    private lateinit var languageSettingsModel: LanguageSettingsModel

    fun initLanguageSettings(context: Context) {
        val position = LocaleSwitcherHelper.newInstance(context).getPosition()
        val model = LanguageSettingsModel()

        model.positionBefore = position

        model.position = position

        languageSettingsModel = model
    }

    fun languageSettingsChanged(): Boolean {
        val model = languageSettingsModel

        val positionBefore = model.positionBefore

        val position = model.position

        return positionBefore.toLong() != position.toLong()
    }

    fun toggleLocale(position: Int) {
        val model = languageSettingsModel

        model.position = position

        languageSettingsModel = model

//        MainManager.toggleLocale(position)
    }

    fun getDisplayName(context: Context): String? {
        val deviceLanguage = LocaleSwitcherHelper.newInstance(context).isDeviceLanguage()
        val displayName = LocaleSwitcherHelper.newInstance(context).getDisplayName()
        return if (deviceLanguage) context.getString(R.string.language_settings_device_language) else displayName
    }
}
