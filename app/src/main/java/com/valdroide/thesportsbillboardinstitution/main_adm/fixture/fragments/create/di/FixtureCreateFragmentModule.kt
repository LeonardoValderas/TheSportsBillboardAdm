package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di

import android.content.Context
import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class FixtureCreateFragmentModule(val view: FixtureCreateFragmentView, val context: Context) {

    @Provides
    @Singleton
    fun provideFixtureCreateFragmentView(): FixtureCreateFragmentView = this.view

    @Provides
    @Singleton
    fun provideFixtureCreateFragmentPresenter(view: FixtureCreateFragmentView, event: EventBus, interactor: FixtureCreateFragmentInteractor): FixtureCreateFragmentPresenter
            = FixtureCreateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideFixtureCreateFragmentInteractor(repository: FixtureCreateFragmentRepository): FixtureCreateFragmentInteractor
            = FixtureCreateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideFixtureCreateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): FixtureCreateFragmentRepository
            = FixtureCreateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Named("spinner_menu")
    fun provideSubMenuSpinnerAdapter(context: Fragment, submenus: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, submenus, 1)
//    @Provides
//    @Named("submenu_list")
//    fun provideSubmenuList(): MutableList<Any> = arrayListOf()

    @Provides
    @Named("spinner_field")
    fun provideFieldSpinnerAdapter(context: Fragment, fields: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, fields, 4)

//    @Provides
//    @Named("field_list")
//    fun provideFieldList(): MutableList<Any> = arrayListOf()

    @Provides
    @Named("spinner_time")
    fun provideTimeSpinnerAdapter(context: Fragment, times: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, times, 5)

//    @Provides
//    @Named("time_list")
//    fun provideTimeList(): MutableList<Any> = arrayListOf()

    @Provides
    @Named("spinner_tournament")
    fun provideTournamentSpinnerAdapter(context: Fragment, tournaments: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, tournaments, 6)

    @Provides
    @Named("spinner_team_local")
    fun provideTeamLocalSpinnerAdapter(context: Fragment, teams_local: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, teams_local, 7)

    @Provides
    @Named("spinner_team_visite")
    fun provideTeamVisiteSpinnerAdapter(context: Fragment, teams_visite: MutableList<Any>): GenericSpinnerAdapter =
            GenericSpinnerAdapter(null, context, teams_visite, 7)

    @Provides
   fun provideList(): MutableList<Any> = arrayListOf()
}