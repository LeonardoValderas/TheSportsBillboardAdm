package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.events

import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer

class NavigationActivityEvent {

    var type: Int = 0
    var error: String? = null
    var menus: MutableList<MenuDrawer>? = null

    companion object {
        val GETMENU = 0
        val ERROR = 1
    }
}
