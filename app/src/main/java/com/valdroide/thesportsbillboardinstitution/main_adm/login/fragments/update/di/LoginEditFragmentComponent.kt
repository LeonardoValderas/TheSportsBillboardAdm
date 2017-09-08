package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.LoginEditFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter.LoginEditFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LoginEditFragmentModule::class, LibsModule::class))
interface LoginEditFragmentComponent {

    fun getPresenter(): LoginEditFragmentPresenter
    fun getAdapter(): LoginEditFragmentAdapter

}