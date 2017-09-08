package com.valdroide.thesportsbillboardinstitution.main_adm.account.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.account.*
import com.valdroide.thesportsbillboardinstitution.main_adm.account.ui.AccountAdmActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountAdmActivityModule(val view: AccountAdmActivityView) {

    @Provides
    @Singleton
    fun provideAccountAdmAdmActivityView(): AccountAdmActivityView =
            this.view

    @Provides
    @Singleton
    fun provideAccountAdmAdmActivityPresenter(view: AccountAdmActivityView, event: EventBus, interactor: AccountAdmActivityInteractor): AccountAdmActivityPresenter =
            AccountAdmActivityPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideAccountAdmActivityInteractor(repository: AccountAdmActivityRepository): AccountAdmActivityInteractor =
            AccountAdmActivityInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideAccountAdmActivityRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): AccountAdmActivityRepository =
            AccountAdmActivityRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}