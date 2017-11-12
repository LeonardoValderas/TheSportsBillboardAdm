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

    override fun getPlayerForIdSubMenu(context: Context, id_submenu: Int) {
        showProgressSetVisivility()
        interactor.getPlayerForIdSubMenu(context, id_submenu)
    }

    override fun getSanction(context: Context, id_Sanction: Int) {
        showProgressSetVisivility()
        interactor.getSanction(context, id_Sanction)
    }

    override fun saveSanction(context: Context, sanction: Sanction) {
        showProgressSetVisivility()
        interactor.saveSanction(context, sanction)
    }

    override fun updateSanction(context: Context, Sanction: Sanction) {
        showProgressSetVisivility()
        interactor.updateSanction(context, Sanction)
    }

    override fun getSubMenusAndPlayers(context: Context) {
        showProgressSetVisivility()
        interactor.getSubMenusAndPlayers(context)
    }

    private fun showProgressSetVisivility() {
        view.showProgressDialog()
        view.setVisibilityViews(View.INVISIBLE)
    }

    private fun hideProgressSetVisivility() {
        view.hideProgressDialog()
        view.setVisibilityViews(View.VISIBLE)
    }

    @Subscribe
    override fun onEventMainThread(event: SanctionCreateFragmentEvent) {
        when (event.type) {
            SanctionCreateFragmentEvent.GETSANCTION -> {
                view.setSanctionUpdate(event.sanction!!)
                view.fillViewUpdate()
                hideProgressSetVisivility()
            }
            SanctionCreateFragmentEvent.SAVESANCTION -> {
                hideProgressSetVisivility()
                view.saveSuccess()
                view.cleanViews()
            }
            SanctionCreateFragmentEvent.UPDATESANCTION -> {
                hideProgressSetVisivility()
                view.editSuccess()
                view.cleanViews()
            }
            SanctionCreateFragmentEvent.GETSUBMENUPLAYER -> {
                view.setSubMenusAndPlayers(event.subMenuDrawers!!, event.players!!)
     //           hideProgressSetVisivility()
            }
            SanctionCreateFragmentEvent.GETPLAYERFORID -> {
                view.setPlayersForId(event.players!!)
                hideProgressSetVisivility()
            }
            SanctionCreateFragmentEvent.ERROR -> {
                hideProgressSetVisivility()
                view.setError(event.error!!)
            }
        }
    }
}