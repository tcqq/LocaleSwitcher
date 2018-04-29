package com.example.localeswitcher.data.adapter;

import android.support.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * @author Alan Dreamer
 * @since 04/13/2018 Created
 */
public class BaseAdapter extends FlexibleAdapter<AbstractFlexibleItem> {

    public BaseAdapter(@Nullable List<AbstractFlexibleItem> items, @Nullable Object listeners) {
        super(items, listeners, true);
    }
}
