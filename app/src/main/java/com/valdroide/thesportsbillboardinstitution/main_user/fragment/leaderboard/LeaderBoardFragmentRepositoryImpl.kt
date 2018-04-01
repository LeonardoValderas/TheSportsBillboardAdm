package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.events.LeaderBoardFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class LeaderBoardFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : LeaderBoardFragmentRepository {
    private var response: WSResponse? = null
    private var leaderBoards: MutableList<LeaderBoard>? = null

    override fun getLeaderBoards(context: Context, id_submenu: Int) {
        try {
            apiService.getLeaderBoards(id_submenu)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    leaderBoards = result.leaderboard
                                   // leaderBoards = leaderBoardsProcess(result.leaderboard)
                                    post(LeaderBoardFragmentEvent.GETLEADERBOARD, leaderBoards)
                                } else {
                                    post(LeaderBoardFragmentEvent.ERROR, response!!.MESSAGE)
                                }
                            } else {
                                post(LeaderBoardFragmentEvent.ERROR, context.getString(
                                        R.string.null_response))
                            }
                        } else {
                            post(LeaderBoardFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LeaderBoardFragmentEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LeaderBoardFragmentEvent.ERROR, e.message!!)
        }
    }

//    private fun leaderBoardsProcess(leaderBoards: MutableList<LeaderBoard>?): MutableList<LeaderBoard>?{
//            Observable.fromIterable(leaderBoards).subscribeOn(scheduler.schedulerCom())
//                    .subscribe { onNext ->
//                        var res = onNext.GF.toInt() - onNext.GE.toInt()
//                        onNext.DF = res.toString()
//                    }
//        return leaderBoards
//    }


    private fun post(type: Int, leaderBoards: MutableList<LeaderBoard>?) {
        post(type, leaderBoards, null)
    }

    private fun post(type: Int, error: String) {
        post(type, null, error)
    }

    private fun post(type: Int, leaderBoards: MutableList<LeaderBoard>?, error: String?) {
        val event = LeaderBoardFragmentEvent()
        event.type = type
        event.leaderBoards = leaderBoards
        event.error = error
        eventBus.post(event)
    }
}
