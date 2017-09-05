package com.valdroide.thesportsbillboardinstitution.main_user.navigation.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.NavigationActivityPresenter
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(NavigationActivityModule::class, LibsModule::class))
interface NavigationActivityComponent {
      //fun inject(activity: NavigationActivity)
      fun getPresenter(): NavigationActivityPresenter
   //   fun getAdapter(): CustomExpandableListAdapter
      fun getListString(): List<String>
  //  fun getExpandableListData(): Map<String, List<SubMenuDrawer>>

}
