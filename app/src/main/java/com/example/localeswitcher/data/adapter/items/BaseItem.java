package com.example.localeswitcher.data.adapter.items;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * * This class will benefit of the already implemented methods (getter and setters) in
 * {@link eu.davidea.flexibleadapter.items.AbstractFlexibleItem}.
 *
 * @author Alan Dreamer
 * @since 04/14/2018 Created
 */
public abstract class BaseItem<VH extends FlexibleViewHolder>
        extends AbstractFlexibleItem<VH> {

    protected String mId;

    public BaseItem(String id) {
        this.mId = id;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof BaseItem) {
            BaseItem inItem = (BaseItem) inObject;
            return this.mId.equals(inItem.mId);
        }
        return false;
    }

    /**
     * Override this method too, when using functionalities like StableIds, Filter or CollapseAll.
     * FlexibleAdapter is making use of HashSet to improve performance, especially in big list.
     */
    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }
}
