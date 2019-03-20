package com.example.localeswitcher.pref

import com.chibatching.kotpref.KotprefModel

/**
 * @author Alan Dreamer
 * @since 07/20/2018 Created
 */
object LanguageSettingsPref : KotprefModel() {
    override val kotprefName = "language_settings"
    var displayName by stringPref("", "display_name")
    var language by stringPref("", "language")
    var country by stringPref("", "country")
    var position by intPref(0, "position")
    var deviceLanguage by booleanPref(true, "device_language")
}