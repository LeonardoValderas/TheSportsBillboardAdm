package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.*
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.adapters.SanctionFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SanctionFragmentModule(val view: SanctionFragmentView, fragment: Fragment) {
    @Provides
    @Singleton
    fun provideSanctionFragmentView(): SanctionFragmentView = this.view

    @Provides
    @Singleton
    fun provideSanctionFragmentPresenter(view: SanctionFragmentView, event: EventBus, interactor: SanctionFragmentInteractor): SanctionFragmentPresenter
            = SanctionFragmentPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun provideSanctionFragmentInteractor(repository: SanctionFragmentRepository): SanctionFragmentInteractor
            = SanctionFragmentInteractorImpl(repository)


    @Provides
    @Singleton
    fun provideSanctionFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): SanctionFragmentRepository
            = SanctionFragmentRepositoryImpl(event!!, apiService, scheduler)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideSanctionFragmentAdapter(Sanctions: MutableList<Sanction>, fragment: Fragment): SanctionFragmentAdapter
            = SanctionFragmentAdapter(Sanctions, fragment)


    @Provides
    @Singleton
    fun provideListSanction(): MutableList<Sanction>  = arrayListOf()

}