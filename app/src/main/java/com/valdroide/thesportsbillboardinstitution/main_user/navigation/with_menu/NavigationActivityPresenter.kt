package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu

import android.content.Context

import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.events.NavigationActivityEvent

interface NavigationActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getMenus(context: Context)
    fun onEventMainThread(event: NavigationActivityEvent)
}
