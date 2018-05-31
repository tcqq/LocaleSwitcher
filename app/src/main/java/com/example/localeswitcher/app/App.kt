package com.example.localeswitcher.app

import android.app.Application
import android.os.StrictMode
import com.example.localeswitcher.BuildConfig
import timber.log.Timber

/**
 * @author Alan Dreamer
 * @since 05/30/2018 Created
 */
class App : Application() {

    var isDebug: Boolean = false

    override fun onCreate() {
        isDebug = BuildConfig.DEBUG
        if (isDebug) {
            setStrictMode()
        }
        super.onCreate()
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}
