package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.events.PlayerUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.PlayerUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import org.greenrobot.eventbus.Subscribe

class PlayerUpdateFragmentPresenterImpl(var view: PlayerUpdateFragmentView, val event: EventBus, val interactor: PlayerUpdateFragmentInteractor) : PlayerUpdateFragmentPresenter {

    override fun getViewPresenter(): PlayerUpdateFragmentView = view

    override fun setViewPresenter(view: PlayerUpdateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getPlayers(context: Context) {
        view.showSwipeRefreshLayout()
        interactor.getPlayers(context)
    }

    override fun activeUnActivePlayer(context: Context, player: Player) {
        view.showSwipeRefreshLayout()
        interactor.activeUnActivePlayer(context, player)
    }

    override fun deletePlayer(context: Context, player: Player) {
        view.showSwipeRefreshLayout()
        interactor.deletePlayer(context, player)
    }

    @Subscribe
    override fun onEventMainThread(event: PlayerUpdateFragmentEvent) {
        when (event.type) {
            PlayerUpdateFragmentEvent.PLAYERS -> {
                view.hideSwipeRefreshLayout()
                view.setAllPlayers(event.players!!)
            }
            PlayerUpdateFragmentEvent.UPDATE -> {
                view.hideSwipeRefreshLayout()
                view.updatePlayerSuccess()
            }
            PlayerUpdateFragmentEvent.DELETE -> {
                view.hideSwipeRefreshLayout()
                view.deletePlayerSuccess()
            }
            PlayerUpdateFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
    }
}