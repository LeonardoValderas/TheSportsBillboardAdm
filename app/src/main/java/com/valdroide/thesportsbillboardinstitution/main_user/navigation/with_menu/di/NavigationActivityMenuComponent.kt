package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui.NavigationActivity
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(NavigationActivityMenuModule::class, LibsModule::class))
interface NavigationActivityMenuComponent {
    fun inject(activity: NavigationActivity)
  //  fun getListString(): List<String>
   // fun getPresenter(): NavigationActivityPresenter
}
