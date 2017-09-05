package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.*
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.PlayerFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.adapters.PlayerFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlayerFragmentModule(val view: PlayerFragmentView, fragment: Fragment) {
    @Provides
    @Singleton
    fun providePlayerFragmentView(): PlayerFragmentView = this.view

    @Provides
    @Singleton
    fun providePlayerFragmentPresenter(view: PlayerFragmentView, event: EventBus, interactor: PlayerFragmentInteractor): PlayerFragmentPresenter
            = PlayerFragmentPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun providePlayerFragmentInteractor(repository: PlayerFragmentRepository): PlayerFragmentInteractor
            = PlayerFragmentInteractorImpl(repository)


    @Provides
    @Singleton
    fun providePlayerFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): PlayerFragmentRepository
            = PlayerFragmentRepositoryImpl(event!!, apiService, scheduler)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun providePlayerFragmentAdapter(Players: MutableList<Player>, fragment: Fragment): PlayerFragmentAdapter
            = PlayerFragmentAdapter(Players, fragment)


    @Provides
    @Singleton
    fun provideListPlayer(): MutableList<Player>  = arrayListOf()

}