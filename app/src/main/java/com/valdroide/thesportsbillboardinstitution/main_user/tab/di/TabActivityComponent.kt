package com.valdroide.thesportsbillboardinstitution.main_user.tab.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.TabActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TabActivityModule::class, LibsModule::class))
interface TabActivityComponent {
    fun inject(tabActivity: TabActivity)
}