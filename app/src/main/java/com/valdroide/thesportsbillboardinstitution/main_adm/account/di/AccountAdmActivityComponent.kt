package com.valdroide.thesportsbillboardinstitution.main_adm.account.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.account.AccountAdmActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.account.ui.AccountAdmActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AccountAdmActivityModule::class, LibsModule::class))
interface AccountAdmActivityComponent {
    fun inject(activity: AccountAdmActivity)
}