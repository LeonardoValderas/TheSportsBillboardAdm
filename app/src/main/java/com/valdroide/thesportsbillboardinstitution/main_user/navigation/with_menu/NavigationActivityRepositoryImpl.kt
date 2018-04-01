package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.events.NavigationActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class NavigationActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : NavigationActivityRepository {
    private var response: WSResponse? = null
    private var menus: MutableList<MenuDrawer>? = null

    override fun getMenus(context: Context) {
        try {
            apiService.getMenus()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    menus = result.menus
                                    if(menus == null)
                                        post(NavigationActivityEvent.ERROR, context.getString(R.string.generic_error_response))
                                    else
                                        post(NavigationActivityEvent.GETMENU, menus!!)
                                } else {
                                    post(NavigationActivityEvent.ERROR, response!!.MESSAGE)
                                }
                            } else {
                                post(NavigationActivityEvent.ERROR, context.getString(
                                        R.string.null_response))
                            }
                        } else {
                            post(NavigationActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(NavigationActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(NavigationActivityEvent.ERROR, e.message!!)
        }
    }

    private fun post(type: Int, menus: MutableList<MenuDrawer>) {
        post(type, menus, null)
    }

    private fun post(type: Int, error: String) {
        post(type, null, error)
    }

    private fun post(type: Int, menus: MutableList<MenuDrawer>?, error: String?) {
        val event = NavigationActivityEvent()
        event.type = type
        event.menus = menus
        event.error = error
        eventBus.post(event)
    }
}
