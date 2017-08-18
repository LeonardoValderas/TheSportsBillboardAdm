package com.valdroide.thesportsbillboardinstitution.main.account.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.account.*
import com.valdroide.thesportsbillboardinstitution.main.account.ui.AccountActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountActivityModule(val view: AccountActivityView) {
    @Provides
    @Singleton
    fun provideAccountActivityView(): AccountActivityView {
        return this.view
    }

    @Provides
    @Singleton
    fun provideAccountActivityPresenter(view: AccountActivityView, event: EventBus, interactor: AccountActivityInteractor): AccountActivityPresenter {
        return AccountActivityPresenterImpl(view, event, interactor)
    }

    @Provides
    @Singleton
    fun provideAccountActivityInteractor(repository: AccountActivityRepository): AccountActivityInteractor {
        return AccountActivityInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun provideAccountActivityRepository(event: EventBus?, apiService: ApiService): AccountActivityRepository {
        return AccountActivityRepositoryImpl(event!!, apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}