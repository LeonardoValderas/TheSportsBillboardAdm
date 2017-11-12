package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.TeamUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.TeamUpdateFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TeamUpdateFragmentModule::class, LibsModule::class))
interface TeamUpdateFragmentComponent {
    fun getPresenter(): TeamUpdateFragmentPresenter
    fun getAdapter(): TeamUpdateFragmentAdapter
}