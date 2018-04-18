package com.valdroide.thesportsbillboardinstitution.main_user.tab.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.tab.*
import com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.TabActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TabActivityModule(val view: TabActivityView) {

    @Provides
    @Singleton
    fun provideTabActivityView(): TabActivityView =
            this.view

    @Provides
    @Singleton
    fun provideTabActivityPresenter(view: TabActivityView, event: EventBus, interactor: TabActivityInteractor): TabActivityPresenter =
            TabActivityPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideTabActivityInteractor(repository: TabActivityRepository): TabActivityInteractor =
            TabActivityInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideTabActivityRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): TabActivityRepository =
            TabActivityRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}