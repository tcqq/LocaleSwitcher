package com.example.localeswitcher.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.example.localeswitcher.data.manager.EventManager;
import com.tcqq.localeswitcher.LocaleSwitcher;


/**
 * Created by nuc on 3/12/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final int defaultLayoutId = 0;
    private final int defaultToolbarId = 0;

    private ViewDataBinding binding;

    protected int getLayoutId() {
        return defaultLayoutId;
    }

    protected int getToolbarId() {
        return defaultToolbarId;
    }

    protected boolean enabledHomeUp() {
        return true;
    }

    protected boolean enableEventBus() {
        return false;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        final LocaleSwitcher localeSwitcher = new LocaleSwitcher(newBase);
        if (!localeSwitcher.isDeviceLanguage()) {
            newBase = localeSwitcher.configureBaseContext(newBase);
        }
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);
        startUi(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        stopUi();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Context getContext() {
        return this;
    }

    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    protected void startUi(Bundle state) {

        if (enableEventBus()) {
            registerEventBus();
        }

        int layoutId = getLayoutId();
        if (layoutId == defaultLayoutId) {
            return;
        }
        binding = DataBindingUtil.setContentView(this, layoutId);

        Toolbar toolbar = findViewById(getToolbarId());

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            if (enabledHomeUp()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeButtonEnabled(true);
                }
            }
        }
    }

    protected void stopUi() {
        if (enableEventBus()) {
            unregisterEventBus();
        }
    }

    protected <T extends ViewDataBinding> T getBinding() {
        return (T) binding;
    }

    public void registerEventBus() {
        EventManager.register(this);
    }

    public void unregisterEventBus() {
        EventManager.unregister(this);
    }
}
