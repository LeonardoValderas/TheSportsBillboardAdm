package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.di

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.*
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.adapters.LeaderBoardFragmentAdapter
import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LeaderBoardFragmentModule(val view: LeaderBoardFragmentView, val context: Context) {
    @Provides
    @Singleton
    fun provideLeaderBoardFragmentView(): LeaderBoardFragmentView = this.view

    @Provides
    @Singleton
    fun provideLeaderBoardFragmentPresenter(view: LeaderBoardFragmentView, event: EventBus, interactor: LeaderBoardFragmentInteractor): LeaderBoardFragmentPresenter
            = LeaderBoardFragmentPresenterImpl(view, event, interactor)


    @Provides
    @Singleton
    fun provideLeaderBoardFragmentInteractor(repository: LeaderBoardFragmentRepository): LeaderBoardFragmentInteractor
            = LeaderBoardFragmentInteractorImpl(repository)


    @Provides
    @Singleton
    fun provideLeaderBoardFragmentRepository(event: EventBus?, apiService: ApiService, scheduler: SchedulersInterface): LeaderBoardFragmentRepository
            = LeaderBoardFragmentRepositoryImpl(event!!, apiService, scheduler)


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val client = ApiClient()
        return client.getApiService()
    }

    @Provides
    @Singleton
    fun provideLeaderBoardFragmentAdapter(context: Context, layoutResourceId: Int, leaderBoards: MutableList<LeaderBoard>): LeaderBoardFragmentAdapter
            = LeaderBoardFragmentAdapter(context, layoutResourceId, leaderBoards)

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideLayoutResourceId(): Int = R.layout.gridview_item_leaderboard

    @Provides
    @Singleton
    fun provideListLeaderBoard(): MutableList<LeaderBoard>  = arrayListOf()

}