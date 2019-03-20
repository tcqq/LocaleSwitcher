package com.example.localeswitcher.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.localeswitcher.manager.LocaleSwitcherManager
import com.example.localeswitcher.pref.LanguageSettingsPref


/**
 * Base Activity class, used to initialize global configuration.
 *
 * @author Alan Dreamer
 * @since 03/12/2016 Created
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        if (LanguageSettingsPref.deviceLanguage) {
            super.attachBaseContext(newBase)
        } else {
            super.attachBaseContext(LocaleSwitcherManager.configureBaseContext(newBase))
        }
    }
}
