package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.SanctionCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SanctionCreateFragmentModule::class, LibsModule::class))
interface SanctionCreateFragmentComponent {
    fun getPresenter(): SanctionCreateFragmentPresenter
    @Named("spinner_menu")
    fun getAdapterSubMenus(): GenericSpinnerAdapter
    @Named("spinner_player")
    fun getAdapterPlayer(): GenericSpinnerAdapter
}