package com.example.localeswitcher.activity

import android.content.Intent
import android.os.Bundle
import com.example.localeswitcher.R
import com.example.localeswitcher.event.MainEvent
import com.example.localeswitcher.manager.LanguageSettingsManager
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Alan Dreamer
 * @since 04/29/2018 Created
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        // Initialize language settings
        LanguageSettingsManager.initLanguageSettings(this)

        button.setOnClickListener {
            startActivity(Intent(this, LanguageSettingsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMainEvent(event: MainEvent) {
        if (event.resetActivity) {
            recreate()
        }
    }
}


