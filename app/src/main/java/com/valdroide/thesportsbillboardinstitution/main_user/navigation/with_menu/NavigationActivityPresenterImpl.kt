package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.events.NavigationActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui.NavigationActivityView
import org.greenrobot.eventbus.Subscribe

class NavigationActivityPresenterImpl(private val view: NavigationActivityView?, private val eventBus: EventBus, private val interactor: NavigationActivityInteractor) : NavigationActivityPresenter {

    override fun onCreate() {
        eventBus.register(this)
    }

    override fun onDestroy() {
        eventBus.unregister(this)
    }

    override fun getMenus(context: Context) {
        interactor.getMenus(context)
    }

    @Subscribe
    override fun onEventMainThread(event: NavigationActivityEvent) {
        if (this.view != null) {
            when (event.type) {
                NavigationActivityEvent.GETMENU -> {
                    view.setMenuDrawers(event.menus!!)
                    view.hideProgressBar()
                }
                NavigationActivityEvent.ERROR -> {
                    view.hideProgressBar()
                    view.setError(event.error!!)
                }
            }
        }
    }
}
