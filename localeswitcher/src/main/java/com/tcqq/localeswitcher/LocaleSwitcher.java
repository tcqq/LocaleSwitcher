package com.tcqq.localeswitcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Locale switcher.
 * <p>
 * Created by Alan Dreamer on 04/10/18.
 */
public class LocaleSwitcher {
    private static final String PREF_NAME = "locale";
    private static final String DEVICE_LANGUAGE = "device_language";
    private static final String POSITION = "position";
    private static final String DISPLAY_NAME = "display_name";
    private static final String LANGUAGE = "language";
    private static final String COUNTRY = "country";

    private SharedPreferences mPrefs;

    private Context mContext;
    private String mDisplayName;
    private String mLanguage;
    private String mCountry;

    public LocaleSwitcher(Context context) {
        mContext = context;
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public Context configureBaseContext(Context context) {
        return updateResources(context, getLocale());
    }

    public String getDisplayName() {
        return mPrefs.getString(DISPLAY_NAME, null);
    }

    public int getPosition() {
        return mPrefs.getInt(POSITION, 0);
    }

    public boolean isDeviceLanguage() {
        return mPrefs.getBoolean(DEVICE_LANGUAGE, true);
    }

    private Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    public LocaleSwitcher setDisplayName(String name) {
        mDisplayName = name;
        return this;
    }

    public LocaleSwitcher setPosition(int position) {
        mPrefs.edit()
                .putInt(POSITION, position)
                .apply();
        return this;
    }

    public LocaleSwitcher setLocale(@NonNull String language, @Nullable String country) {
        mLanguage = language;
        mCountry = country;
        return this;
    }

    public LocaleSwitcher setDeviceLanguage(boolean deviceLanguage) {
        mPrefs.edit()
                .putBoolean(DEVICE_LANGUAGE, deviceLanguage)
                .apply();
        return this;
    }

    public void apply() {
        if (!TextUtils.isEmpty(mDisplayName)) {
            mPrefs.edit()
                    .putString(DISPLAY_NAME, mDisplayName)
                    .apply();
        }
        if (!TextUtils.isEmpty(mLanguage)) {
            mPrefs.edit()
                    .putString(LANGUAGE, mLanguage)
                    .putString(COUNTRY, mCountry)
                    .apply();

            updateResources(mContext, getLocale());
        }
    }

    private Locale getLocale() {
        final String language = mPrefs.getString(LANGUAGE, null);
        final String country = mPrefs.getString(COUNTRY, null);
        return new Locale(language, country);
    }
}
