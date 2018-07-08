package com.example.localeswitcher.activity

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.localeswitcher.R
import com.example.localeswitcher.adapter.LanguagesAdapter
import com.example.localeswitcher.adapter.items.LanguagesItem
import com.example.localeswitcher.event.MainEvent
import com.example.localeswitcher.manager.LanguageSettingsManager
import com.tcqq.localeswitcher.LocaleSwitcherHelper
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemAnimator
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.reactivex.Observable
import io.reactivex.Observer
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
    private lateinit var localeList: List<Locale>
    private lateinit var headerData: Observable<String>
    private lateinit var listsData: Observable<String>
    private lateinit var adapter: LanguagesAdapter
    private val items = ArrayList<AbstractFlexibleItem<*>>()
    private var languagesList: List<AbstractFlexibleItem<*>> = items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_settings)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setTitle(R.string.language_settings_title)
        }
        text_view_selected_language.text = LanguageSettingsManager.getDisplayName(this)
        initData()
        Observable
                .merge(headerData, listsData)
                .subscribeOn(Schedulers.io())
                .`as`<ObservableSubscribeProxy<String>>(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        items.add(LanguagesItem(items.size.toString(), s))
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e.localizedMessage)
                    }

                    override fun onComplete() {
                        initRecycler()
                    }
                })
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
                val locale = localeList.toTypedArray()[position - 1]
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

            setActivatedPosition(position)
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

    private fun initData() {
        headerData = Observable.just(getString(R.string.language_settings_device_language))
        localeList = Arrays.asList(
                Locale("en"),
                Locale("zh", "CN"),
                Locale("ja"),
                Locale("ko"),
                Locale("ar"))
        listsData = Observable
                .just(localeList)
                .flatMap { Observable.fromIterable(it) }
                .map { locale -> Locale(locale.language, locale.country).getDisplayName(Locale(locale.language, locale.country)) }
    }

    private fun initRecycler() {
        adapter = LanguagesAdapter(languagesList, this)
        adapter.mode = SelectableAdapter.Mode.SINGLE
        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.itemAnimator = FlexibleItemAnimator()
        setActivatedPosition(LocaleSwitcherHelper.newInstance(this).getPosition())
    }

    private fun setActivatedPosition(position: Int) {
        activatedPosition = position
        adapter.toggleSelection(position)
    }
}