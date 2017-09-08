package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.LoginCreateFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LoginCreateFragmentModule::class, LibsModule::class))
interface LoginCreateFragmentComponent {
    fun getPresenter(): LoginCreateFragmentPresenter
}