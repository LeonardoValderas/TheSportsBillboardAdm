package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface MenuSubMenuActivityView {
    fun setMenuSubMenu(menuDrawers: MutableList<MenuDrawer>, subMenuDrawers: MutableList<SubMenuDrawer>)
    fun setError(error: String)
    fun eventSuccess(msg: String)
    fun refreshSpinners()
    fun showProgressBar()
    fun hideProgressBar()
    fun menuSaveSuccess()
    fun validateAlert()
}