package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.events.PlayerCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.PlayerCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import org.greenrobot.eventbus.Subscribe

class PlayerCreateFragmentPresenterImpl(var view: PlayerCreateFragmentView, val event: EventBus, val interactor: PlayerCreateFragmentInteractor) : PlayerCreateFragmentPresenter {

    override fun getViewPresenter(): PlayerCreateFragmentView = view

    override fun setViewPresenter(view: PlayerCreateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getPlayer(context: Context, id_player: Int) {
        interactor.getPlayer(context, id_player)
    }

    override fun savePlayer(context: Context, player: Player) {
        interactor.savePlayer(context, player)
    }

    override fun updatePlayer(context: Context, player: Player) {
        interactor.updatePlayer(context, player)
    }

    override fun getPositionsSubMenus(context: Context) {
        interactor.getPositionsSubMenus(context)
    }

    @Subscribe
    override fun onEventMainThread(event: PlayerCreateFragmentEvent) {
        when (event.type) {
            PlayerCreateFragmentEvent.GETPLAYER -> {
                view.setPlayerUpdate(event.player!!)
                view.fillViewUpdate()
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
            }
            PlayerCreateFragmentEvent.SAVEPLAYER -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.saveSuccess()
                view.cleanViews()
            }
            PlayerCreateFragmentEvent.UPDATEPLAYER -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.editSuccess()
                view.cleanViews()
            }
            PlayerCreateFragmentEvent.POSITIONSSUBMENUS -> {
                view.setVisibilityViews(View.VISIBLE)
                view.setPositionsAndSubMenus(event.positions!!, event.submenus!!)
                view.hideProgressDialog()
            }
            PlayerCreateFragmentEvent.ERROR -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.setError(event.error!!)
            }
        }
    }
}