package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard

import android.content.Context

interface LeaderBoardFragmentInteractor {
    fun getLeaderBoards(context: Context, id_submenu: Int)
}