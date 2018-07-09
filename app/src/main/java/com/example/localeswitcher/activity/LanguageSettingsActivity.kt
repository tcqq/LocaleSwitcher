package com.example.localeswitcher.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.localeswitcher.R
import com.example.localeswitcher.adapter.LanguagesAdapter
import com.example.localeswitcher.adapter.items.LanguagesItem
import com.example.localeswitcher.event.MainEvent
import com.example.localeswitcher.manager.LanguageSettingsManager
import com.tcqq.localeswitcher.LocaleSwitcherHelper
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemAnimator
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_language_settings.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
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
    private var items = ArrayList<AbstractFlexibleItem<*>>()

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
        if (LanguageSettingsManager.languageSettingsChanged()) {
            EventBus.getDefault().post(MainEvent(true))
        }
    }

    override fun onItemClick(view: View, position: Int): Boolean {
        if (position != activatedPosition) {
            val localeSwitcherHelper = LocaleSwitcherHelper(this)
            if (position == 0) {
                localeSwitcherHelper
                        .setPosition(position)
                        .setDeviceLanguage(true)
                        .setDisplayName(getString(R.string.language_settings_device_language))
                        .apply()
            } else {
                val locale = getLocaleList().toTypedArray()[position - 1]
                val language = locale.language
                val country = locale.country
                val displayName = Locale(language, country).getDisplayName(Locale(language, country))

                localeSwitcherHelper
                        .setPosition(position)
                        .setLocale(language, country)
                        .setDeviceLanguage(false)
                        .setDisplayName(displayName)
                        .apply()
            }

            LanguageSettingsManager.toggleLocale(position)
            recreate()
        }
        return true
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
        Observable
                .merge(getHeaderList(), getLanguagesList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String?> {
                    override fun onComplete() {
                        val adapter = LanguagesAdapter(items, this@LanguageSettingsActivity)
                        adapter.mode = SelectableAdapter.Mode.SINGLE
                        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(this@LanguageSettingsActivity)
                        recycler_view.adapter = adapter
                        recycler_view.setHasFixedSize(true)
                        recycler_view.itemAnimator = FlexibleItemAnimator()

                        val position = LocaleSwitcherHelper.newInstance(this@LanguageSettingsActivity).getPosition()
                        activatedPosition = position
                        adapter.toggleSelection(position)

                        text_view_selected_language.text = LanguageSettingsManager.getDisplayName(this@LanguageSettingsActivity)
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: String) {
                        items.add(LanguagesItem(items.size.toString(), t))
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e.localizedMessage)
                    }
                })
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