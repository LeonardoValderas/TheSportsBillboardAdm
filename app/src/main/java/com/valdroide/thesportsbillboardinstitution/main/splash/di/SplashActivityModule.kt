package com.valdroide.thesportsbillboardinstitution.main.splash.di

import android.app.Activity
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.splash.*
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SplashActivityModule(val view: SplashActivityView){

    @Provides
    @Singleton
    fun provideSplashActivityView(): SplashActivityView {
        return this.view
    }

    @Provides
    @Singleton
    fun provideSplashActivityPresenter(view: SplashActivityView, event: EventBus, interactor: SplashActivityInteractor): SplashActivityPresenter {
        return SplashActivityPresenterImpl(view, event, interactor)
    }

    @Provides
    @Singleton
    fun provideSplashActivityInteractor(repository: SplashActivityRepository): SplashActivityInteractor {
        return SplashActivityInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSplashActivityRepository(event: EventBus?, apiService: ApiService): SplashActivityRepository {
        return SplashActivityRepositoryImpl(event!!, apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}
