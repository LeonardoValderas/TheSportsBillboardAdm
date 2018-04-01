package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.SanctionFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.adapters.SanctionFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SanctionFragmentModule::class, LibsModule::class))
interface SanctionFragmentComponent {
    fun inject(fragment: SanctionFragment)
}