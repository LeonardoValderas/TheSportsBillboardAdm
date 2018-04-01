package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu

import android.content.Context

import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.events.NavigationActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.ui.NavigationActivityView

import org.greenrobot.eventbus.Subscribe

class NavigationActivityPresenterImpl(private val view: NavigationActivityView?, private val eventBus: EventBus, private val interactor: NavigationActivityInteractor) : NavigationActivityPresenter {

    override fun onCreate() {
        eventBus.register(this)
    }

    override fun onDestroy() {
        eventBus.unregister(this)
    }

    override fun getMenusAndSubMenus(context: Context) {
        interactor.getMenusAndSubMenus(context)
    }

    @Subscribe
    override fun onEventMainThread(event: NavigationActivityEvent) {
        if (this.view != null) {
            when (event.type) {
                NavigationActivityEvent.GETMENU_SUBMENU -> {
                    view.setMenuAndSubMenu(event.menus!!, event.submenus!!)
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
