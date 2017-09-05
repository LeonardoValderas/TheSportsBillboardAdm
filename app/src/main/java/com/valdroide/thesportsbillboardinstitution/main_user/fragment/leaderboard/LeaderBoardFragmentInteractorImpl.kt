package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard

import android.content.Context

class LeaderBoardFragmentInteractorImpl(private val repository: LeaderBoardFragmentRepository) : LeaderBoardFragmentInteractor {

    override fun getLeaderBoards(context: Context, id_submenu: Int) {
        repository.getLeaderBoards(context, id_submenu)
    }
}
