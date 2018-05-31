package com.example.localeswitcher.ui.adapter.items;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.example.localeswitcher.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * @author Alan Dreamer
 * @since 05/24/2018 Created
 */
public class LanguagesItem extends AbstractItem<LanguagesItem.ViewHolder> {

    private String displayName;

    public LanguagesItem(String id, String displayName) {
        super(id);
        this.displayName = displayName;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_material_single_line_list_with_avatar_and_icon;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ViewHolder holder, int position, List payloads) {
        int accentColor = getThemeAccentColor(holder.icon.getContext());
        holder.text.setText(displayName);
        holder.avatarFrame.setVisibility(View.GONE);
        holder.icon.setImageResource(R.drawable.ic_check_black_24dp);
        holder.icon.setColorFilter(accentColor);

        if (adapter.isSelected(position)) {
            holder.icon.setVisibility(View.VISIBLE);
        } else {
            holder.icon.setVisibility(View.GONE);
        }
    }

    static final class ViewHolder extends FlexibleViewHolder {

        FrameLayout avatarFrame;
        AppCompatTextView text;
        AppCompatImageView icon;

        ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            avatarFrame = view.findViewById(R.id.avatar_frame);
            text = view.findViewById(R.id.text);
            icon = view.findViewById(R.id.icon);
        }
    }

    public static int getThemeAccentColor(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }
}
