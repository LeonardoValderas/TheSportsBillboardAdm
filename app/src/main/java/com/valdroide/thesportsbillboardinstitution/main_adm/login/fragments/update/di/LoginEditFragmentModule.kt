package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter.LoginEditFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginEditFragmentModule(val view: LoginEditFragmentView, val listener: OnItemClickListener) {

    @Provides
    @Singleton
    fun provideLoginEditFragmentView(): LoginEditFragmentView = this.view

    @Provides
    @Singleton
    fun provideLoginEditFragmentPresenter(view: LoginEditFragmentView, event: EventBus, interactor: LoginEditFragmentInteractor): LoginEditFragmentPresenter
            = LoginEditFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideLoginEditFragmentInteractor(repository: LoginEditFragmentRepository): LoginEditFragmentInteractor
            = LoginEditFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideLoginEditFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): LoginEditFragmentRepository
            = LoginEditFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideLoginEditFragmentAdapter(logins: MutableList<Login>, listener: OnItemClickListener, fragment: Fragment): LoginEditFragmentAdapter
            = LoginEditFragmentAdapter(logins, listener, fragment)

    @Provides
    @Singleton
    fun provideListLogins(): MutableList<Login> = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): OnItemClickListener = listener
}