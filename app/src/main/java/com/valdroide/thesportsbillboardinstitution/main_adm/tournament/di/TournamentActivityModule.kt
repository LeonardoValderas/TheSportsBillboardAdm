package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.*
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivityView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TournamentActivityModule(val view: TournamentActivityView) {

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
}