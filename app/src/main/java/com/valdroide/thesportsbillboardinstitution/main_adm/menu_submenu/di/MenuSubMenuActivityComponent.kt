package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.MenuSubMenuActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.adapter.MenuActivityAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.adapter.SubMenuActivityAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MenuSubMenuActivityModule::class, LibsModule::class))
interface MenuSubMenuActivityComponent {
    fun getPresenter(): MenuSubMenuActivityPresenter
    fun getAdapterMenus(): MenuActivityAdapter
    fun getAdapterSubMenus(): SubMenuActivityAdapter
}