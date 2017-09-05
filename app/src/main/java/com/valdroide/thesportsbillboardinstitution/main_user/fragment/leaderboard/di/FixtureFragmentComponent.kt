package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.LeaderBoardFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.adapters.LeaderBoardFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LeaderBoardFragmentModule::class, LibsModule::class))
interface LeaderBoardFragmentComponent {
    fun getPresenter(): LeaderBoardFragmentPresenter
    fun getAdapter(): LeaderBoardFragmentAdapter
}

