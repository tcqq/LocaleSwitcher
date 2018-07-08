package com.example.localeswitcher.application

import android.app.Application
import com.example.localeswitcher.BuildConfig
import timber.log.Timber

/**
 * @author Alan Dreamer
 * @since 05/30/2018 Created
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
