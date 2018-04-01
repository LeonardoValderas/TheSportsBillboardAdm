package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu

import android.content.Context

import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.events.NavigationActivityEvent

interface NavigationActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getMenusAndSubMenus(context: Context)
    fun onEventMainThread(event: NavigationActivityEvent)
}
