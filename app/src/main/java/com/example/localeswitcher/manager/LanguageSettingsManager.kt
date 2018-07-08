package com.example.localeswitcher.manager

import android.content.Context
import com.example.localeswitcher.R
import com.example.localeswitcher.model.LanguageSettingsModel
import com.tcqq.localeswitcher.LocaleSwitcherHelper

/**
 * @author Alan Dreamer
 * @since 04/27/2018 Created
 */
object LanguageSettingsManager {

    fun initLanguageSettings(context: Context) {
        val position = LocaleSwitcherHelper.newInstance(context).getPosition()
        LanguageSettingsModel.positionBefore = position
        LanguageSettingsModel.position = position
    }

    fun languageSettingsChanged(): Boolean {
        return LanguageSettingsModel.positionBefore != LanguageSettingsModel.position
    }

    fun toggleLocale(position: Int) {
        LanguageSettingsModel.position = position
    }

    fun getDisplayName(context: Context): String? {
        val deviceLanguage = LocaleSwitcherHelper.newInstance(context).isDeviceLanguage()
        val displayName = LocaleSwitcherHelper.newInstance(context).getDisplayName()
        return if (deviceLanguage) context.getString(R.string.language_settings_device_language) else displayName
    }
}
