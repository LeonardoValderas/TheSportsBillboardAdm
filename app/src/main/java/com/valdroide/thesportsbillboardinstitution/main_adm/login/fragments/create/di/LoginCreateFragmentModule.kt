package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui.LoginCreateFragmentView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginCreateFragmentModule(val view: LoginCreateFragmentView) {

    @Provides
    @Singleton
    fun provideLoginCreateFragmentView(): LoginCreateFragmentView = this.view

    @Provides
    @Singleton
    fun provideLoginCreateFragmentPresenter(view: LoginCreateFragmentView, event: EventBus, interactor: LoginCreateFragmentInteractor): LoginCreateFragmentPresenter
            = LoginCreateFragmentPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun provideLoginCreateFragmentInteractor(repository: LoginCreateFragmentRepository): LoginCreateFragmentInteractor
            = LoginCreateFragmentInteractorImpl(repository)


    @Provides
    @Singleton
    fun provideLoginCreateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): LoginCreateFragmentRepository
            = LoginCreateFragmentRepositoryImpl(event!!, apiService, scheduler)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}