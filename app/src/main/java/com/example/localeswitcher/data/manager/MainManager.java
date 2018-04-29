package com.example.localeswitcher.data.manager;

import com.example.localeswitcher.data.event.MainEvent;

/**
 * Settings.
 *
 * @author Alan Dreamer
 * @since 04/17/2018 Created
 */
public class MainManager extends EventManager {

    private static MainManager mInstance;

    private MainManager() {
    }

    synchronized public static MainManager onInstance() {
        if (mInstance == null) {
            mInstance = new MainManager();
        }
        return mInstance;
    }

    /**
     * Reset {@link com.example.localeswitcher.ui.MainActivity} Activity.
     */
    public void resetActivity() {
        final MainEvent event = new MainEvent();
        event.setResetActivity();
        post(event);
    }
}
