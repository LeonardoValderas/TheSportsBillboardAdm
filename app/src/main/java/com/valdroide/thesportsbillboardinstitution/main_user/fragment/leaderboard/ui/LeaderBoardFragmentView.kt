package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard

interface LeaderBoardFragmentView {
    fun setLeaderBoards(lidearBoards:MutableList<LeaderBoard>)
    fun setError(error: String)
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}