package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.events

import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard

class LeaderBoardFragmentEvent {

    var type: Int = 0
    var error: String? = null
    var leaderBoards: MutableList<LeaderBoard>? = null

    companion object {
        val GETLEADERBOARD = 0
        val ERROR = 1
    }
}