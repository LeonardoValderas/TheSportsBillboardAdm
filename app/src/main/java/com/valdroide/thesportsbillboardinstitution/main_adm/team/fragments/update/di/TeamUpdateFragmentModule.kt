package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.TeamUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
@Module
class TeamUpdateFragmentModule(val view: TeamUpdateFragmentView,
                               val context: Context) {

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
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideTeamUpdateFragmentAdapter(context: Context): TeamUpdateFragmentAdapter
            = TeamUpdateFragmentAdapter(context)

    @Provides
    @Singleton
    fun provideListTeams(): MutableList<Team> = arrayListOf()
}
