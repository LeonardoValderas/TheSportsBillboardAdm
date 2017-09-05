package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.events.PlayerFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.PlayerFragmentView
import org.greenrobot.eventbus.Subscribe

class PlayerFragmentPresenterImpl(var view: PlayerFragmentView, val event: EventBus, val interactor: PlayerFragmentInteractor) : PlayerFragmentPresenter {


    override fun getViewPresenter(): PlayerFragmentView = view

    override fun setViewPresenter(view: PlayerFragmentView) {
       this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getPlayers(context: Context, id_submenu: Int) {
        interactor.getPlayers(context, id_submenu)
    }

    @Subscribe
    override fun onEventMainThread(event: PlayerFragmentEvent) {
        if (view != null) {
            when (event.type) {
                PlayerFragmentEvent.PLAYERS -> {
                    view.hideSwipeRefreshLayout()
                    view.setPlayers(event.players!!)
                }
                PlayerFragmentEvent.ERROR -> {
                    view.hideSwipeRefreshLayout()
                    view.setError(event.error!!)
                }
            }
        }
    }
}