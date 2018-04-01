package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer

interface NavigationActivityView {
    fun setMenuDrawers(menus: MutableList<MenuDrawer>)
    fun setError(error: String)
    fun hideProgressBar()
    fun showProgressBar()

}