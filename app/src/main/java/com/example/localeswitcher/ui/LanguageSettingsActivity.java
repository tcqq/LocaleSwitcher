package com.example.localeswitcher.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.localeswitcher.R;
import com.example.localeswitcher.data.adapter.BaseAdapter;
import com.example.localeswitcher.data.adapter.items.LanguagesItem;
import com.example.localeswitcher.data.manager.LanguageSettingsManager;
import com.example.localeswitcher.data.manager.MainManager;
import com.example.localeswitcher.data.util.AndroidUtil;
import com.example.localeswitcher.data.util.DataUtil;
import com.tcqq.localeswitcher.LocaleSwitcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.FlexibleItemAnimator;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Language settings. Select language and switch application language locale.
 *
 * @author Alan Dreamer
 * @since 04/12/2018 Created
 */
public class LanguageSettingsActivity extends BaseActivity implements
        FlexibleAdapter.OnItemClickListener {

    /**
     * Custom implementation of FlexibleAdapter
     */
    private BaseAdapter mAdapter;
    private List<AbstractFlexibleItem> mItems = new ArrayList<>();

    /**
     * Language activated position and list data
     */
    private int mActivatedPosition = -1;
    private final List<Locale> mLocaleList =
            Arrays.asList(
                    new Locale("en"),
                    new Locale("zh", "CN"),
                    new Locale("ja"),
                    new Locale("ko"),
                    new Locale("ar"));

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language_settings;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void startUi(Bundle state) {
        super.startUi(state);
        setTitle(getString(R.string.language_settings_title));
        initializeData();
        initializeRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (LanguageSettingsManager.onInstance().languageSettingsChanged()) {
            MainManager.onInstance().resetActivity();
        }
    }

    private void initializeData() {
        mItems.add(new LanguagesItem("H", getString(R.string.language_settings_device_language)));
        for (int i = 0; i < mLocaleList.size(); i++) {
            final String language = mLocaleList.get(i).getLanguage();
            final String country = mLocaleList.get(i).getCountry();
            final String displayName = new Locale(language, country).getDisplayName(new Locale(language, country));

            mItems.add(new LanguagesItem("I" + i, displayName));
        }
    }

    private void initializeRecyclerView() {
        mAdapter = new BaseAdapter(getLocaleList(), this);
        mAdapter.setMode(SelectableAdapter.Mode.SINGLE);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new SmoothScrollLinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new FlexibleItemAnimator());

        final int position = new LocaleSwitcher(getContext()).getPosition();
        setActivatedPosition(position);

        TextView textViewSelectedLanguage = findViewById(R.id.tv_selected_language);
        final String displayName = LanguageSettingsManager.onInstance().getDisplayName(getContext());
        textViewSelectedLanguage.setText(displayName);
    }

    @Override
    public boolean onItemClick(View view, int position) {
        if (!DataUtil.equals(position, mActivatedPosition)) {
            final LocaleSwitcher localeSwitcher = new LocaleSwitcher(getContext());
            if (DataUtil.equals(position, 0)) {
                localeSwitcher
                        .setPosition(position)
                        .setDeviceLanguage(true)
                        .setDisplayName(getString(R.string.language_settings_device_language))
                        .apply();
            } else {
                final Locale locale = mLocaleList.toArray(new Locale[0])[position - 1];
                final String language = locale.getLanguage();
                final String country = locale.getCountry();
                final String displayName = new Locale(language, country).getDisplayName(new Locale(language, country));

                localeSwitcher
                        .setPosition(position)
                        .setLocale(language, country)
                        .setDeviceLanguage(false)
                        .setDisplayName(displayName)
                        .apply();
            }

            setActivatedPosition(position);
            LanguageSettingsManager.onInstance().toggleLocale(position);
            AndroidUtil.recreateActivity(this, true);
        }
        return true;
    }

    private void setActivatedPosition(int position) {
        mActivatedPosition = position;
        mAdapter.toggleSelection(position);
    }

    private List<AbstractFlexibleItem> getLocaleList() {
        return mItems;
    }
}