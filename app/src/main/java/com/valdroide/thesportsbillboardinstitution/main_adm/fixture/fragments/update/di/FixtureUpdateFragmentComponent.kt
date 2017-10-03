package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.FixtureUpdateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.adapter.FixtureUpdateFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(FixtureUpdateFragmentModule::class, LibsModule::class))
interface FixtureUpdateFragmentComponent {

    fun getPresenter(): FixtureUpdateFragmentPresenter
    fun getAdapter(): FixtureUpdateFragmentAdapter
}