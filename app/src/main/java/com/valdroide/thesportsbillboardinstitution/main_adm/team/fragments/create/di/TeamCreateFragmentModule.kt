package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.*
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui.TeamCreateFragmentView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TeamCreateFragmentModule(val view: TeamCreateFragmentView) {

    @Provides
    @Singleton
    fun provideTeamCreateFragmentView(): TeamCreateFragmentView = this.view

    @Provides
    @Singleton
    fun provideTeamCreateFragmentPresenter(view: TeamCreateFragmentView, event: EventBus, interactor: TeamCreateFragmentInteractor): TeamCreateFragmentPresenter
            = TeamCreateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideTeamCreateFragmentInteractor(repository: TeamCreateFragmentRepository): TeamCreateFragmentInteractor
            = TeamCreateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideTeamCreateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): TeamCreateFragmentRepository
            = TeamCreateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }
}