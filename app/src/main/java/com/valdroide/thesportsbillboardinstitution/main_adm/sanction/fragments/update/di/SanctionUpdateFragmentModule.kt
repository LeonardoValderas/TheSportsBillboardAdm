package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.SanctionUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.adapter.SanctionUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener_2
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SanctionUpdateFragmentModule(val view: SanctionUpdateFragmentView, val listener: GenericOnItemClickListener_2) {

    @Provides
    @Singleton
    fun provideSanctionUpdateFragmentView(): SanctionUpdateFragmentView = this.view

    @Provides
    @Singleton
    fun provideSanctionUpdateFragmentPresenter(view: SanctionUpdateFragmentView, event: EventBus, interactor: SanctionUpdateFragmentInteractor): SanctionUpdateFragmentPresenter
            = SanctionUpdateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideSanctionUpdateFragmentInteractor(repository: SanctionUpdateFragmentRepository): SanctionUpdateFragmentInteractor
            = SanctionUpdateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideSanctionUpdateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): SanctionUpdateFragmentRepository
            = SanctionUpdateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideSanctionUpdateFragmentAdapter(sanction: MutableList<Sanction>, listener: GenericOnItemClickListener_2, fragment: Fragment): SanctionUpdateFragmentAdapter
            = SanctionUpdateFragmentAdapter(sanction, listener, fragment)

    @Provides
    @Singleton
    fun provideListSanction(): MutableList<Sanction> = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): GenericOnItemClickListener_2 = listener
}