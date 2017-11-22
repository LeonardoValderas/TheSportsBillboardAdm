package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di

import android.app.Activity
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.*
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivityView
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.adapter.TournamentActivityAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.adapter.TournamentActivitySpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TournamentActivityModule(val view: TournamentActivityView, val
listener: GenericOnItemClickListener.actualUnActual) {

    @Provides
    @Singleton
    fun provideTournamentActivityView(): TournamentActivityView =
            this.view

    @Provides
    @Singleton
    fun provideTournamentActivityPresenter(view: TournamentActivityView, event: EventBus, interactor: TournamentActivityInteractor): TournamentActivityPresenter =
            TournamentActivityPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideTournamentActivityInteractor(repository: TournamentActivityRepository): TournamentActivityInteractor =
            TournamentActivityInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideTournamentActivityRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): TournamentActivityRepository =
            TournamentActivityRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideTournamentActivitySpinnerAdapter(tournaments: MutableList<Tournament>, context: Activity): TournamentActivitySpinnerAdapter =
            TournamentActivitySpinnerAdapter(tournaments, context)

    @Provides
    @Singleton
    fun provideListTournaments(): MutableList<Tournament> = arrayListOf()

    @Provides
    @Singleton
    fun provideTournamentActivityAdapter(subMenus: MutableList<SubMenuDrawer>?, tournament: Tournament, listener: GenericOnItemClickListener.actualUnActual,
                                         activity: Activity): TournamentActivityAdapter =
            TournamentActivityAdapter(subMenus, tournament, listener, activity, false)

    @Provides
    @Singleton
    fun provideListSubMenus(): MutableList<SubMenuDrawer> = arrayListOf()

    @Provides
    @Singleton
    fun provideTournament(): Tournament = Tournament()

    @Provides
    @Singleton
    fun provideOnItemClick(): GenericOnItemClickListener.actualUnActual = listener
}