package com.valdroide.thesportsbillboardinstitution.main_user.splash.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.splash.*
import com.valdroide.thesportsbillboardinstitution.main_user.splash.ui.SplashActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SplashActivityModule(val view: SplashActivityView){

    @Provides
    @Singleton
    fun provideSplashActivityView(): SplashActivityView = this.view


    @Provides
    @Singleton
    fun provideSplashActivityPresenter(view: SplashActivityView, event: EventBus, interactor: SplashActivityInteractor): SplashActivityPresenter =
            SplashActivityPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun provideSplashActivityInteractor(repository: SplashActivityRepository): SplashActivityInteractor =
            SplashActivityInteractorImpl(repository)


    @Provides
    @Singleton
    fun provideSplashActivityRepository(event: EventBus?, apiService: ApiService): SplashActivityRepository =
            SplashActivityRepositoryImpl(event!!, apiService)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}
