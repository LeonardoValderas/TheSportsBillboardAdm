package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.events.LeaderBoardFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragmentView

import org.greenrobot.eventbus.Subscribe

class LeaderBoardFragmentPresenterImpl(private val view: LeaderBoardFragmentView?, private val eventBus: EventBus, private val interactor: LeaderBoardFragmentInteractor) : LeaderBoardFragmentPresenter {

    override fun onCreate() {
        eventBus.register(this)
    }

    override fun onDestroy() {
        eventBus.unregister(this)
    }

    override fun getLeaderBoards(context: Context, id_submenu: Int) {
        interactor.getLeaderBoards(context, id_submenu)
    }

    @Subscribe
    override fun onEventMainThread(event: LeaderBoardFragmentEvent) {
        if (this.view != null) {
            when (event.type) {
                LeaderBoardFragmentEvent.GETLEADERBOARD -> {
                    view.setLeaderBoards(event.leaderBoards!!)
                    view.hideSwipeRefreshLayout()
                }
                LeaderBoardFragmentEvent.ERROR -> {
                    view.hideSwipeRefreshLayout()
                    view.setError(event.error!!)
                }
            }
        }
    }
}

