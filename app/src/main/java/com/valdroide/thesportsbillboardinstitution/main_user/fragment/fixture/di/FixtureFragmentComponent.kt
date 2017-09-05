package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.FixtureFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(FixtureFragmentModule::class, LibsModule::class))
interface FixtureFragmentComponent {

    fun getPresenter(): FixtureFragmentPresenter
    fun getAdapter(): FixtureFragmentAdapter

}

