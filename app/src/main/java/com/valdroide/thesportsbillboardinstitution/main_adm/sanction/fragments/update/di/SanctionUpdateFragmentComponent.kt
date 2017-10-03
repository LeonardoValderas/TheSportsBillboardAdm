package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.SanctionUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.adapter.SanctionUpdateFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SanctionUpdateFragmentModule::class, LibsModule::class))
interface SanctionUpdateFragmentComponent {

    fun getPresenter(): SanctionUpdateFragmentPresenter
    fun getAdapter(): SanctionUpdateFragmentAdapter
}