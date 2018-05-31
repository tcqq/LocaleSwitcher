package com.example.localeswitcher.data.manager

import android.content.Context
import com.example.localeswitcher.data.event.MainEvent
import com.example.localeswitcher.data.models.LanguageSettingsModel
import com.example.localeswitcher.data.utils.EventBusUtil
import com.tcqq.localeswitcher.LocaleSwitcherHelper

/**
 * Main.
 * <br></br>
 * <br></br>Press the back key will fall back to the first fragment.
 * <br></br>Synchronize the Navigation selected item with the current Fragment position.
 * <br></br>Click on Navigation item and synchronize the Fragment position.
 *
 * @author Alan Dreamer
 * @see com.changrui.tt.ui.fragment.MainFragment
 *
 * @since 04/01/2018 Created
 */
object MainManager {

    /**
     * Language settings
     */
    private lateinit var languageSettingsModel: LanguageSettingsModel

    /**
     * Send reset [com.changrui.tt.ui.activity.MainActivity] event.
     */
    fun resetActivity() {
        val event = MainEvent()
        event.isResetActivity = true
        EventBusUtil.post(event)
    }

    fun initializeLanguageSettings(context: Context) {
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
    }

}
