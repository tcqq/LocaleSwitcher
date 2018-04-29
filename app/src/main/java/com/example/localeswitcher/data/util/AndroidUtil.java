package com.example.localeswitcher.data.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * @author Alan Dreamer
 * @since 04/29/2018 Created
 */
public class AndroidUtil {

    public static void recreateActivity(Activity activity, boolean animate) {
        Intent restartIntent = new Intent(activity, activity.getClass());

        Bundle extras = activity.getIntent().getExtras();
        if (extras != null) {
            restartIntent.putExtras(extras);
        }

        if (animate) {
            ActivityCompat.startActivity(
                    activity,
                    restartIntent,
                    ActivityOptionsCompat
                            .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                            .toBundle()
            );
        } else {
            activity.startActivity(restartIntent);
            activity.overridePendingTransition(0, 0);
        }

        activity.finish();
    }
}
