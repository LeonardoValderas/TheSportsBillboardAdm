package com.valdroide.thesportsbillboardinstitution.main_user.account.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.account.*
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountActivityModule(val view: AccountActivityView) {

    @Provides
    @Singleton
    fun provideAccountActivityView(): AccountActivityView =
            this.view

    @Provides
    @Singleton
    fun provideAccountActivityPresenter(view: AccountActivityView, event: EventBus, interactor: AccountActivityInteractor): AccountActivityPresenter =
            AccountActivityPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideAccountActivityInteractor(repository: AccountActivityRepository): AccountActivityInteractor =
            AccountActivityInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideAccountActivityRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): AccountActivityRepository =
            AccountActivityRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}