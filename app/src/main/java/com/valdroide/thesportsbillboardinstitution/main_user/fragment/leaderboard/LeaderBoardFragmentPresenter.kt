package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.events.LeaderBoardFragmentEvent

interface LeaderBoardFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getLeaderBoards(context: Context, id_submenu: Int)
    fun onEventMainThread(event: LeaderBoardFragmentEvent)
}
