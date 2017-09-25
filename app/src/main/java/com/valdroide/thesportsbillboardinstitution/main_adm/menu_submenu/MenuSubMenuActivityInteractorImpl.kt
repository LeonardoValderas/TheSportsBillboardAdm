package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

class MenuSubMenuActivityInteractorImpl(val repository: MenuSubMenuActivityRepository) : MenuSubMenuActivityInteractor {

    override fun getMenuSubMenu(context: Context) {
        repository.getMenuSubMenu(context)
    }

    override fun saveMenu(context: Context, menu: MenuDrawer) {
        repository.saveMenu(context, menu)
    }

    override fun updateMenu(context: Context, menu: MenuDrawer) {
        repository.updateMenu(context, menu)
    }

    override fun activeOrUnActiveMenu(context: Context, menu: MenuDrawer) {
        repository.activeOrUnActiveMenu(context, menu)
    }

    override fun deleteMenu(context: Context, menu: MenuDrawer) {
        repository.deleteMenu(context, menu)
    }
    override fun saveSubMenu(context: Context, subMenu: SubMenuDrawer) {
        repository.saveSubMenu(context, subMenu)
    }

    override fun updateSubMenu(context: Context, subMenu: SubMenuDrawer) {
        repository.updateSubMenu(context, subMenu)
    }

    override fun activeOrUnActiveSubMenu(context: Context, subMenu: SubMenuDrawer) {
        repository.activeOrUnActiveSubMenu(context, subMenu)
    }

    override fun deleteSubMenu(context: Context, subMenu: SubMenuDrawer) {
        repository.deleteSubMenu(context, subMenu)
    }
}