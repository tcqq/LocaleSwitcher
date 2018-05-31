package com.example.localeswitcher.data.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat

/**
 * @author Alan Dreamer
 * @since 04/29/2018 Created
 */
object AndroidUtil {

    fun recreateActivity(activity: Activity, animate: Boolean) {
        val restartIntent = Intent(activity, activity.javaClass)

        val extras = activity.intent.extras
        if (extras != null) {
            restartIntent.putExtras(extras)
        }

        if (animate) {
            ActivityCompat.startActivity(
                    activity,
                    restartIntent,
                    ActivityOptionsCompat
                            .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                            .toBundle()
            )
        } else {
            activity.startActivity(restartIntent)
            activity.overridePendingTransition(0, 0)
        }

        activity.finish()
    }
}
