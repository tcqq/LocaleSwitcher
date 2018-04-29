package com.example.localeswitcher.data.model;

/**
 * Language settings.
 *
 * @author Alan Dreamer
 * @since 04/22/2018 Created
 */
public class LanguageSettingsModel {

    private int mPositionBefore;

    private int mPosition;

    public LanguageSettingsModel() {
    }

    public int getPositionBefore() {
        return mPositionBefore;
    }

    public void setPositionBefore(int positionBefore) {
        this.mPositionBefore = positionBefore;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }
}
