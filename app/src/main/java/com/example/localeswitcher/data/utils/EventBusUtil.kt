package com.example.localeswitcher.data.utils

import org.greenrobot.eventbus.EventBus

/**
 * @author Alan Dreamer
 * @since 05/28/2018 Created
 */
object EventBusUtil {

    fun register(subscriber: Any) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber)
        }
    }

    fun unregister(subscriber: Any) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

    fun post(event: Any) {
        EventBus.getDefault().post(event)
    }
}
