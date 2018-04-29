package com.example.localeswitcher.data.event;

/**
 * @author Alan Dreamer
 * @since 04/17/2018 Created
 */
public class MainEvent {

    private boolean mResetActivity;

    public boolean isResetActivity() {
        return mResetActivity;
    }

    public void setResetActivity() {
        this.mResetActivity = true;
    }
}
