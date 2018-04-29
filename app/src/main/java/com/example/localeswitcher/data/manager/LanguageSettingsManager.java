package com.example.localeswitcher.data.manager;

import android.content.Context;

import com.example.localeswitcher.R;
import com.example.localeswitcher.data.model.LanguageSettingsModel;
import com.example.localeswitcher.data.util.DataUtil;
import com.tcqq.localeswitcher.LocaleSwitcher;


/**
 * Language settings.
 *
 * @author Alan Dreamer
 * @since 04/27/2018 Created
 */
public class LanguageSettingsManager {

    private static LanguageSettingsManager mInstance;

    private LanguageSettingsModel mLanguageSettings;

    private LanguageSettingsManager() {
    }

    synchronized public static LanguageSettingsManager onInstance() {
        if (mInstance == null) {
            mInstance = new LanguageSettingsManager();
        }
        return mInstance;
    }

    public void initializeLanguageSettings(Context context) {
        final int position = new LocaleSwitcher(context).getPosition();
        final LanguageSettingsModel model = new LanguageSettingsModel();

        model.setPositionBefore(position);

        model.setPosition(position);

        setLanguageSettings(model);
    }

    public boolean languageSettingsChanged() {
        final LanguageSettingsModel model = getLanguageSettings();
        if (DataUtil.isEmpty(model)) return false;

        final int positionBefore = model.getPositionBefore();

        final int position = model.getPosition();

        return !DataUtil.equals(positionBefore, position);
    }

    public void toggleLocale(int position) {
        final LanguageSettingsModel model = getLanguageSettings();

        if (DataUtil.isEmpty(model)) return;

        model.setPosition(position);

        setLanguageSettings(model);
    }

    private LanguageSettingsModel getLanguageSettings() {
        return mLanguageSettings;
    }

    private void setLanguageSettings(LanguageSettingsModel languageSettings) {
        this.mLanguageSettings = languageSettings;
    }

    public String getDisplayName(Context context) {
        final boolean deviceLanguage = new LocaleSwitcher(context).isDeviceLanguage();
        final String displayName = new LocaleSwitcher(context).getDisplayName();
        return deviceLanguage ? context.getString(R.string.language_settings_device_language) : displayName;
    }
}
