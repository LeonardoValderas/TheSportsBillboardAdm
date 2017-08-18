package com.valdroide.thesportsbillboardinstitution.main.account.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main.account.AccountActivityPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AccountActivityModule::class, LibsModule::class))
interface AccountActivityComponent{
    fun getPresenter(): AccountActivityPresenter
}