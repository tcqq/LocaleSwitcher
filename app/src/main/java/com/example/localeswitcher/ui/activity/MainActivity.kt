package com.example.localeswitcher.ui.activity

import android.content.Intent
import android.os.Bundle
import com.example.localeswitcher.R
import com.example.localeswitcher.data.event.MainEvent
import com.example.localeswitcher.data.manager.LanguageSettingsManager
import com.example.localeswitcher.data.utils.AndroidUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Alan Dreamer
 * @since 04/29/2018 Created
 */
class MainActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun toolbarId(): Int {
        return R.id.toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_name)
        // Initialize language settings
        LanguageSettingsManager.initLanguageSettings(this)

        button.setOnClickListener {
            startActivity(Intent(this, LanguageSettingsActivity::class.java))
        }
    }

    override fun enabledHomeUp(): Boolean {
        return false
    }

    override fun enableEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MainEvent) {
        if (event.isResetActivity) {
            AndroidUtil.recreateActivity(this, true)
        }
    }
}


