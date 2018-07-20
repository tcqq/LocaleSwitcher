package com.example.localeswitcher.adapter.items

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import com.example.localeswitcher.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

/**
 * @author Alan Dreamer
 * @since 05/24/2018 Created
 */
data class LanguagesItem(val id: String, val displayName: String) : AbstractFlexibleItem<LanguagesItem.ViewHolder>() {

    override fun getLayoutRes(): Int {
        return R.layout.item_languages
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?, holder: ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        if (holder != null) {
            val context = holder.itemView.context
            holder.text.text = displayName
            holder.icon.setImageResource(R.drawable.ic_check_black_24dp)
            holder.icon.setColorFilter(getThemeAccentColor(context))
            if (adapter != null) {
                if (adapter.isSelected(position)) {
                    holder.icon.visibility = View.VISIBLE
                } else {
                    holder.icon.visibility = View.GONE
                }
            }
        }
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var text: AppCompatTextView = view.findViewById(R.id.text)
        var icon: AppCompatImageView = view.findViewById(R.id.icon)
    }

    private fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, value, true)
        return value.data
    }
}
