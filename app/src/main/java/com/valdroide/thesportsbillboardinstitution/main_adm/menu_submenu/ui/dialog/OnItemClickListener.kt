package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface OnItemClickListener {
    fun onClickSaveMenu(context: Context, menu: MenuDrawer)
    fun onClickUpdateMenu(context: Context, menu: MenuDrawer)
    fun onClickSaveSubMenu(context: Context, submenu: SubMenuDrawer)
    fun onClickUpdateSubMenu(context: Context, submenu: SubMenuDrawer)
}