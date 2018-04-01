package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.events

import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

class NavigationActivityEvent {

    var type: Int = 0
    var error: String? = null
    var menus: List<MenuDrawer>? = null
    var submenus: List<SubMenuDrawer>? = null

    companion object {
        val GETMENU_SUBMENU = 0
        val ERROR = 1
    }
}
