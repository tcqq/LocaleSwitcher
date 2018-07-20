package com.example.localeswitcher.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.localeswitcher.R
import com.example.localeswitcher.adapter.items.LanguagesItem
import com.example.localeswitcher.event.MainEvent
import com.example.localeswitcher.manager.LocaleSwitcherManager
import com.example.localeswitcher.model.LanguageSettingsModel
import com.example.localeswitcher.pref.LanguageSettingsPref
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_language_settings.*
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * Language settings. Select language and switch application language locale.
 *
 * @author Alan Dreamer
 * @since 04/12/2018 Created
 */
class LanguageSettingsActivity : BaseActivity(),
        FlexibleAdapter.OnItemClickListener {

    private var activatedPosition = -1
    private var languageItems = ArrayList<IFlexible<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_settings)
        initActionBar()
        initRecyclerView()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setTitle(R.string.language_settings_title)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (LanguageSettingsModel.positionBefore
                != LanguageSettingsModel.position) {
            EventBus.getDefault().post(MainEvent(true))
        }
    }

    override fun onItemClick(view: View, position: Int): Boolean {
        if (position != activatedPosition) {
            if (position == 0) {
                LanguageSettingsPref.position = position
                LanguageSettingsPref.deviceLanguage = true
                LanguageSettingsPref.displayName = getString(R.string.language_settings_device_language)
            } else {
                val locale = getLocaleList().toTypedArray()[position - 1]
                val language = locale.language
                val country = locale.country
                val displayName = Locale(language, country).getDisplayName(Locale(language, country))

                LanguageSettingsPref.position = position
                LanguageSettingsPref.language = language
                LanguageSettingsPref.country = country
                LanguageSettingsPref.deviceLanguage = false
                LanguageSettingsPref.displayName = displayName
                LocaleSwitcherManager.configureBaseContext(this)
            }
            LanguageSettingsModel.position = position
            recreate()
        }
        return false
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

    private fun initRecyclerView() {
        val adapter: FlexibleAdapter<IFlexible<*>> = FlexibleAdapter(getLanguageItems(), this, true)
        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)

        val position = LanguageSettingsPref.position
        activatedPosition = position
        adapter.toggleSelection(position)

        text_view_selected_language.text = LanguageSettingsPref.deviceLanguage.let {
            if (it) return@let getString(R.string.language_settings_device_language)
            else return@let LanguageSettingsPref.displayName
        }
    }


    private fun getLanguageItems(): List<IFlexible<*>> {
        var index = -1
        Observable
                .merge(getHeaderList(), getLanguagesList())
                .subscribe {
                    languageItems.add(LanguagesItem("language" + ++index, it))
                }.isDisposed
        return languageItems
    }

    private fun getHeaderList(): Observable<String> {
        return Observable.just(getString(R.string.language_settings_device_language))
    }

    private fun getLanguagesList(): Observable<String> {
        return Observable
                .just(getLocaleList())
                .flatMap {
                    Observable.fromIterable(it)
                }
                .map { locale ->
                    Locale(locale.language, locale.country).getDisplayName(Locale(locale.language, locale.country))
                }
    }

    private fun getLocaleList(): List<Locale> {
        return Arrays.asList(
                Locale("en"),
                Locale("zh", "CN"),
                Locale("ja"),
                Locale("ko"),
                Locale("ar"))
    }
}