package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.events.PlayerCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.PlayerCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import org.greenrobot.eventbus.Subscribe

class PlayerCreateFragmentPresenterImpl(var view: PlayerCreateFragmentView,
                                        val event: EventBus,
                                        val interactor: PlayerCreateFragmentInteractor) : PlayerCreateFragmentPresenter {

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
        showProgressAndSetVisivility()
        interactor.savePlayer(context, player)
    }

    override fun updatePlayer(context: Context, player: Player) {
        showProgressAndSetVisivility()
        interactor.updatePlayer(context, player)
    }

    override fun savePosition(context: Context, position: Position) {
        showProgressAndSetVisivility()
        interactor.savePosition(context, position)
    }

    override fun updatePosition(context: Context, position: Position) {
        showProgressAndSetVisivility()
        interactor.updatePosition(context, position)
    }

    override fun getPositionsSubMenus(context: Context) {
        showProgressAndSetVisivility()
        interactor.getPositionsSubMenus(context)
    }

    private fun showProgressAndSetVisivility(){
        view.showProgressDialog()
        view.setVisibilityViews(View.INVISIBLE)
    }

    private fun hideProgressAndSetVisivility(){
        view.hideProgressDialog()
        view.setVisibilityViews(View.VISIBLE)
    }

    @Subscribe
    override fun onEventMainThread(event: PlayerCreateFragmentEvent) {
        when (event.type) {
            PlayerCreateFragmentEvent.GETPLAYER -> {
                view.setPlayerUpdate(event.player!!)
                view.fillViewUpdate()
                hideProgressAndSetVisivility()
            }
            PlayerCreateFragmentEvent.SAVEPLAYER -> {
                hideProgressAndSetVisivility()
                view.refreshAdapter()
                view.savePlayerSuccess()
                view.cleanViews()
            }
            PlayerCreateFragmentEvent.UPDATEPLAYER -> {
                hideProgressAndSetVisivility()
                view.refreshAdapter()
                view.editPlayerSuccess()
                view.cleanViews()
            }
            PlayerCreateFragmentEvent.SAVEPOSITION -> {
                hideProgressAndSetVisivility()
                view.savePositionSuccess(event.position!!)
            }
            PlayerCreateFragmentEvent.UPDATEPOSITION -> {
                hideProgressAndSetVisivility()
                view.refreshAdapter()
                view.editPositionSuccess(event.position!!)
            }
            PlayerCreateFragmentEvent.POSITIONSSUBMENUS -> {
                view.setPositionsAndSubMenus(event.positions!!, event.submenus!!)
                hideProgressAndSetVisivility()
            }
            PlayerCreateFragmentEvent.ERROR -> {
                hideProgressAndSetVisivility()
                view.setError(event.error!!)
            }
        }
    }
}