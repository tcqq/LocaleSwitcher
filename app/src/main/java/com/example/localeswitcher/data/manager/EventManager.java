package com.example.localeswitcher.data.manager;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Alan Dreamer
 * @since 04/29/2018 Created
 */
public class EventManager {

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
