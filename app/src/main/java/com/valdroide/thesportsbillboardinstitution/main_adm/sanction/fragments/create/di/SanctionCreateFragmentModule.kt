package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.di

import android.content.Context
import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui.SanctionCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class SanctionCreateFragmentModule(val view: SanctionCreateFragmentView, val context: Context) {

    @Provides
    @Singleton
    fun provideSanctionCreateFragmentView(): SanctionCreateFragmentView = this.view

    @Provides
    @Singleton
    fun provideSanctionCreateFragmentPresenter(view: SanctionCreateFragmentView, event: EventBus, interactor: SanctionCreateFragmentInteractor): SanctionCreateFragmentPresenter
            = SanctionCreateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideSanctionCreateFragmentInteractor(repository: SanctionCreateFragmentRepository): SanctionCreateFragmentInteractor
            = SanctionCreateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideSanctionCreateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): SanctionCreateFragmentRepository
            = SanctionCreateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Named("spinner_menu")
    fun provideSubMenuSpinnerAdapter(context: Fragment, @Named("menus") submenus: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, submenus, 1)

    @Provides
    @Named("menus")
    fun provideListSubMenus(): MutableList<Any> = arrayListOf()

    @Provides
    @Named("spinner_player")
    fun providePlayerSpinnerAdapter(context: Fragment, @Named("players") player: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, player, 3)

    @Provides
    @Named("players")
    fun provideListPlayer(): MutableList<Any> = arrayListOf()
}