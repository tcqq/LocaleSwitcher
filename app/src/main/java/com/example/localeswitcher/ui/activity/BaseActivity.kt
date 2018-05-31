package com.example.localeswitcher.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.localeswitcher.data.utils.EventBusUtil
import com.tcqq.localeswitcher.LocaleSwitcherHelper


/**
 * Created by nuc on 3/12/2016.
 */
abstract class BaseActivity : AppCompatActivity() {

    open fun layoutId(): Int {
        return 0
    }

    open fun toolbarId(): Int {
        return 0
    }

    open fun isFullScreen(): Boolean {
        return false
    }

    open fun enabledHomeUp(): Boolean {
        return true
    }

    open fun enableEventBus(): Boolean {
        return false
    }

    override fun attachBaseContext(base: Context) {
        if (LocaleSwitcherHelper.newInstance(base).isDeviceLanguage().not()) {
            super.attachBaseContext(LocaleSwitcherHelper(base).configureBaseContext())
        } else {
            super.attachBaseContext(base)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutId = layoutId()
        if (layoutId != 0) {
            initLayout(layoutId)
        }
    }

    override fun onDestroy() {
        if (enableEventBus()) {
            unregisterEventBus()
        }
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerEventBus() {
        EventBusUtil.register(this)
    }

    private fun unregisterEventBus() {
        EventBusUtil.unregister(this)
    }

    fun setTitle(title: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

    fun setSubtitle(subtitle: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.subtitle = subtitle
        }
    }

    private fun initLayout(layoutId: Int) {
        if (enableEventBus()) {
            registerEventBus()
        }

        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setContentView(layoutId)

        initToolbar()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(toolbarId())
        if (toolbar != null) {
            if (isFullScreen()) {
                if (toolbar.isShown) {
                    toolbar.visibility = View.GONE
                }
            } else {
                if (!toolbar.isShown) {
                    toolbar.visibility = View.VISIBLE
                }
                setSupportActionBar(toolbar)
                if (enabledHomeUp()) {
                    val actionBar = supportActionBar
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true)
                        actionBar.setHomeButtonEnabled(true)
                    }
                }
            }
        }
    }
}
