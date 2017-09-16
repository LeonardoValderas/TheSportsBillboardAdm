package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.di

import android.support.v4.app.Fragment
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.*
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.PlayerUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter.PlayerUpdateFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PlayerUpdateFragmentModule(val view: PlayerUpdateFragmentView, val listener: OnItemClickListener) {

    @Provides
    @Singleton
    fun providePlayerUpdateFragmentView(): PlayerUpdateFragmentView = this.view

    @Provides
    @Singleton
    fun providePlayerUpdateFragmentPresenter(view: PlayerUpdateFragmentView, event: EventBus, interactor: PlayerUpdateFragmentInteractor): PlayerUpdateFragmentPresenter
            = PlayerUpdateFragmentPresenterImpl(view, event, interactor)

    @Provides
    @Singleton
    fun providePlayerUpdateFragmentInteractor(repository: PlayerUpdateFragmentRepository): PlayerUpdateFragmentInteractor
            = PlayerUpdateFragmentInteractorImpl(repository)

    @Provides
    @Singleton
    fun providePlayerUpdateFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): PlayerUpdateFragmentRepository
            = PlayerUpdateFragmentRepositoryImpl(event!!, apiService, scheduler)

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun providePlayerUpdateFragmentAdapter(players: MutableList<Player>, listener: OnItemClickListener, fragment: Fragment): PlayerUpdateFragmentAdapter
            = PlayerUpdateFragmentAdapter(players, listener, fragment)

    @Provides
    @Singleton
    fun provideListPlayers(): MutableList<Player> = arrayListOf()

    @Provides
    @Singleton
    fun provideOnItemClickListener(): OnItemClickListener = listener
}