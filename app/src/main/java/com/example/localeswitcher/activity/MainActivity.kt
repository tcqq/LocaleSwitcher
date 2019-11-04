package com.example.localeswitcher.activity

import android.content.Intent
import android.os.Bundle
import com.example.localeswitcher.R
import com.example.localeswitcher.event.MainEvent
import com.example.localeswitcher.model.LanguageSettingsModel
import com.example.localeswitcher.pref.LanguageSettingsPref
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Alan Perry
 * @since 04/29/2018 Created
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        initLanguageSettings()

        button.setOnClickListener {
            startActivity(Intent(this, LanguageSettingsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * This event is used to change the locale.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMainEvent(event: MainEvent) {
        if (event.resetActivity) {
            recreate()
        }
    }

    private fun initLanguageSettings() {
        val position = LanguageSettingsPref.position
        LanguageSettingsModel.positionBefore = position
        LanguageSettingsModel.position = position
    }
}


