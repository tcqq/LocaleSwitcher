package com.example.localeswitcher.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.tcqq.localeswitcher.LocaleSwitcherHelper


/**
 * Base Activity class, used to initialize global configuration.
 *
 * @author Alan Dreamer
 * @since 03/12/2016 Created
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        if (LocaleSwitcherHelper.newInstance(base).isDeviceLanguage().not()) {
            super.attachBaseContext(LocaleSwitcherHelper(base).configureBaseContext())
        } else {
            super.attachBaseContext(base)
        }
    }
}
