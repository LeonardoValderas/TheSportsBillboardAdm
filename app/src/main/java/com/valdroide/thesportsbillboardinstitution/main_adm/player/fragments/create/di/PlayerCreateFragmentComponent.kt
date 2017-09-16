package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di

import android.widget.ArrayAdapter
import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.PlayerCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PlayerCreateFragmentModule::class, LibsModule::class))
interface PlayerCreateFragmentComponent {
    fun getPresenter(): PlayerCreateFragmentPresenter
    fun getAdapterPosition(): ArrayAdapter<Position>
    fun getAdapterSubMenus(): ArrayAdapter<SubMenuDrawer>
}