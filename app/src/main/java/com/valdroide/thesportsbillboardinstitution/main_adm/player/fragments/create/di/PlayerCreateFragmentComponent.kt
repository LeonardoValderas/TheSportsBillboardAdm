package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.PlayerCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.adapters.PlayerCreateFragmentPositionSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.adapters.PlayerCreateFragmentSubMenuSpinnerAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PlayerCreateFragmentModule::class, LibsModule::class))
interface PlayerCreateFragmentComponent {
    fun getPresenter(): PlayerCreateFragmentPresenter
    fun getAdapterPosition(): PlayerCreateFragmentPositionSpinnerAdapter
    fun getAdapterSubMenus(): PlayerCreateFragmentSubMenuSpinnerAdapter
}