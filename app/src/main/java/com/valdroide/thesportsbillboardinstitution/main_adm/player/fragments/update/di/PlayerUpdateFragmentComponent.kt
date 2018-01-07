package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.PlayerUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter.PlayerUpdateFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PlayerUpdateFragmentModule::class, LibsModule::class))
interface PlayerUpdateFragmentComponent {

    fun getPresenter(): PlayerUpdateFragmentPresenter
   // fun getAdapter(): PlayerUpdateFragmentAdapter
}