package com.example.localeswitcher.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.localeswitcher.R;
import com.example.localeswitcher.data.event.MainEvent;
import com.example.localeswitcher.data.manager.LanguageSettingsManager;
import com.example.localeswitcher.data.util.AndroidUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Alan Dreamer
 * @since 04/29/2018 Created
 */
public class MainActivity extends BaseActivity {

    @Override
    protected boolean enabledHomeUp() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void startUi(Bundle state) {
        super.startUi(state);
        setTitle(R.string.app_name);
        // Initialize language settings
        LanguageSettingsManager.onInstance().initializeLanguageSettings(getContext());

        Button buttonStart = findViewById(R.id.btn_start);
        buttonStart.setOnClickListener(v -> startActivity(new Intent(getContext(), LanguageSettingsActivity.class)));
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainEvent event) {
        if (event.isResetActivity()) {
            AndroidUtil.recreateActivity(this, true);
        }
    }
}
