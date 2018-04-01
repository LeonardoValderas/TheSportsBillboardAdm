package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di

import android.content.Context
import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.adapters.*
import com.valdroide.thesportsbillboardinstitution.model.entities.*
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
/*
    @Provides
    @Named("spinner_menu")
    fun provideSubMenuSpinnerAdapter(context: Fragment, submenus: MutableList<SubMenuDrawer>): FixtureCreateFragmentSubMenuSpinnerAdapter =
            FixtureCreateFragmentSubMenuSpinnerAdapter(submenus, context.activity)

    @Provides
    @Singleton
    fun provideSubmenuList(): MutableList<SubMenuDrawer> = arrayListOf()

    @Provides
    @Named("spinner_field")
    fun provideFieldSpinnerAdapter(context: Fragment, fields: MutableList<FieldMatch>): FixtureCreateFragmentFieldSpinnerAdapter =
            FixtureCreateFragmentFieldSpinnerAdapter(fields, context.activity)

    @Provides
    @Singleton
    fun provideFieldList(): MutableList<FieldMatch> = arrayListOf()

    @Provides
    @Named("spinner_time")
    fun provideTimeSpinnerAdapter(context: Fragment, times: MutableList<TimeMatch>): FixtureCreateFragmentTimesSpinnerAdapter =
            FixtureCreateFragmentTimesSpinnerAdapter(times, context.activity)

    @Provides
    @Singleton
    fun provideTimeList(): MutableList<TimeMatch> = arrayListOf()

    @Provides
    @Named("spinner_tournament")
    fun provideTournamentSpinnerAdapter(context: Fragment, tournaments: MutableList<Tournament>): FixtureCreateFragmentTournamentSpinnerAdapter =
            FixtureCreateFragmentTournamentSpinnerAdapter(tournaments, context.activity)

    @Provides
    @Singleton
    fun provideTournamentList(): MutableList<Tournament> = arrayListOf()

    @Provides
    @Named("spinner_team_local")
    fun provideTeamLocalSpinnerAdapter(context: Fragment, @Named("teams_local") teams_local: MutableList<Team>): FixtureCreateFragmentTeamSpinnerAdapter =
            FixtureCreateFragmentTeamSpinnerAdapter(teams_local, context.activity)

    @Provides
    @Named("spinner_team_visite")
    fun provideTeamVisiteSpinnerAdapter(context: Fragment, @Named("teams_visite") teams_visite: MutableList<Team>): FixtureCreateFragmentTeamSpinnerAdapter =
            FixtureCreateFragmentTeamSpinnerAdapter(teams_visite, context.activity)

    @Provides
    @Named("teams_local")
    fun provideTeamsLocalList(): MutableList<Team> = arrayListOf()

    @Provides
    @Named("teams_visite")
    fun provideTeamsVisiteList(): MutableList<Team> = arrayListOf()
    */
}