package com.example.localeswitcher.data.adapter.items;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
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
 * @since 04/14/2018 Created
 */
public class LanguagesItem extends BaseItem<LanguagesItem.LanguagesViewHolder> {

    private String mDisplayName;

    public LanguagesItem(String id, String displayName) {
        super(id);
        this.mDisplayName = displayName;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_material_single_line_list_with_avatar_and_icon;
    }

    @Override
    public LanguagesViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new LanguagesViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, LanguagesViewHolder holder, int position, List payloads) {
        final int accentColor = getAccentColor(holder.mText.getContext());
        holder.mText.setText(mDisplayName);
        holder.mAvatarFrame.setVisibility(View.GONE);
        holder.mIcon.setImageResource(R.drawable.ic_check_black_24dp);
        holder.mIcon.setColorFilter(accentColor);

        if (adapter.isSelected(position)) {
            holder.mIcon.setVisibility(View.VISIBLE);
        } else {
            holder.mIcon.setVisibility(View.GONE);
        }
    }

    class LanguagesViewHolder extends FlexibleViewHolder {

        final FrameLayout mAvatarFrame;
        final AppCompatTextView mText;
        final AppCompatImageView mIcon;

        LanguagesViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mAvatarFrame = view.findViewById(R.id.avatar_frame);
            mText = view.findViewById(R.id.text);
            mIcon = view.findViewById(R.id.icon);
        }
    }

    @ColorInt
    private static int getAccentColor(@NonNull final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }
}
