package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.TeamCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui.TeamCreateFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TeamCreateFragmentModule::class, LibsModule::class))
interface TeamCreateFragmentComponent {
    fun inject(fragment: TeamCreateFragment)
}