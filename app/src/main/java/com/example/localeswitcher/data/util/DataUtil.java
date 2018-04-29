package com.example.localeswitcher.data.util;

import android.text.TextUtils;


public final class DataUtil {

    public static boolean isEmpty(String dataValue) {
        return TextUtils.isEmpty(dataValue);
    }

    public static boolean isEmpty(String... dataValues) {
        if (dataValues == null) {
            return true;
        }
        for (String dataValue : dataValues) {
            if (isEmpty(dataValue)) return true;
        }
        return false;
    }

    public static boolean isEmpty(Integer dataValue) {
        return dataValue == 0;
    }

    public static boolean isEmpty(Integer... dataValues) {
        for (Integer dataValue : dataValues) {
            if (isEmpty(dataValue)) return true;
        }
        return false;
    }

    public static boolean isEmpty(Object object) {
        return object == null;
    }

    public static boolean equals(Object left, Object right) {
        if (isEmpty(left) && isEmpty(right)) return true;
        if (isEmpty(left) || isEmpty(right)) return false;

        if (left.equals(right)) return true;

        return false;
    }

    public static boolean equals(String left, String right) {
        if (isEmpty(left) && isEmpty(right)) return true;

        if (left.equals(right)) return true;

        return false;
    }
}
