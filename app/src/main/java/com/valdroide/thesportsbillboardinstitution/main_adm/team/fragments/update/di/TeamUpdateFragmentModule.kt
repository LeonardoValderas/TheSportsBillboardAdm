package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.TeamUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TeamUpdateFragmentModule(val view: TeamUpdateFragmentView, val listener: OnItemClickListener) {

    @Provides
    @Singleton
    fun provideTeamUpdateFragmentView(): TeamUpdateFragmentView = this.view

    @Provides
    @Singleton
    fun provideTeamUpdateFragmentPresenter(view: TeamUpdateFragmentView, event: EventBus, interactor: TeamUpdateFragmentInteractor): TeamUpdateFragmentPresenter
            = TeamUpdateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun provideTeamUpdateFragmentInteractor(repository: TeamUpdateFragmentRepository): TeamUpdateFragmentInteractor
            = TeamUpdateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun provideTeamUpdateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): TeamUpdateFragmentRepository
            = TeamUpdateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideTeamUpdateFragmentAdapter(teams: MutableList<Team>, listener: com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.OnItemClickListener, fragment: Fragment): TeamUpdateFragmentAdapter
            = TeamUpdateFragmentAdapter(teams, listener, fragment)

    @Provides
    @Singleton
    fun provideListTeams(): MutableList<Team> = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.OnItemClickListener = listener
}