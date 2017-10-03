package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.events.SanctionCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui.SanctionCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import org.greenrobot.eventbus.Subscribe

class SanctionCreateFragmentPresenterImpl(var view: SanctionCreateFragmentView, val event: EventBus, val interactor: SanctionCreateFragmentInteractor) : SanctionCreateFragmentPresenter {

    override fun getViewPresenter(): SanctionCreateFragmentView = view

    override fun setViewPresenter(view: SanctionCreateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getPlayerForIdSubMenu(context: Context, id_submenu: Int){
        interactor.getPlayerForIdSubMenu(context, id_submenu)
    }

    override fun getSanction(context: Context, id_Sanction: Int) {
        interactor.getSanction(context, id_Sanction)
    }

    override fun saveSanction(context: Context, sanction: Sanction) {
        interactor.saveSanction(context, sanction)
    }

    override fun updateSanction(context: Context, Sanction: Sanction) {
        interactor.updateSanction(context, Sanction)
    }

    override fun getSubMenusAndPlayers(context: Context) {
        interactor.getSubMenusAndPlayers(context)
    }

    @Subscribe
    override fun onEventMainThread(event: SanctionCreateFragmentEvent) {
        when (event.type) {
            SanctionCreateFragmentEvent.GETSANCTION-> {
                view.setSanctionUpdate(event.sanction!!)
                view.fillViewUpdate()
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
            }
            SanctionCreateFragmentEvent.SAVESANCTION -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.saveSuccess()
                view.cleanViews()
            }
            SanctionCreateFragmentEvent.UPDATESANCTION -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.editSuccess()
                view.cleanViews()
            }
            SanctionCreateFragmentEvent.GETSUBMENUPLAYER -> {
                view.setVisibilityViews(View.VISIBLE)
                view.setSubMenusAndPlayers(event.subMenuDrawers!!, event.players!!)
                view.hideProgressDialog()
            }
            SanctionCreateFragmentEvent.GETPLAYERFORID-> {
                view.setVisibilityViews(View.VISIBLE)
                view.setPlayersForId(event.players!!)
                view.hideProgressDialog()
            }
            SanctionCreateFragmentEvent.ERROR -> {
                view.hideProgressDialog()
                view.setVisibilityViews(View.VISIBLE)
                view.setError(event.error!!)
            }
        }
    }
}