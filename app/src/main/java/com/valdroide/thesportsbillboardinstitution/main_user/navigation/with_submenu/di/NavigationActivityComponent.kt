package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_submenu.ui.NavigationActivity
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(NavigationActivityModule::class, LibsModule::class))
interface NavigationActivityComponent {
    fun inject(activity: NavigationActivity)
  //  fun getListString(): List<String>
   // fun getPresenter(): NavigationActivityPresenter
}
