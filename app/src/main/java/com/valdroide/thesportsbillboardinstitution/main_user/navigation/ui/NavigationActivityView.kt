package com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface NavigationActivityView {
    fun setMenuAndSubMenu(menus:List<MenuDrawer>, submenus: List<SubMenuDrawer>)
    fun setError(error: String)
    fun hideProgressBar()
    fun showProgressBar()

}