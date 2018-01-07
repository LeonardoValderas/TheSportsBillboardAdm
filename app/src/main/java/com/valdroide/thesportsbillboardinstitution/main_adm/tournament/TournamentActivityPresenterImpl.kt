package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events.TournamentActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import org.greenrobot.eventbus.Subscribe

class TournamentActivityPresenterImpl(val view: TournamentActivityView, val event: EventBus, val interactor: TournamentActivityInteractor) : TournamentActivityPresenter {

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getSubMenuTournaments(context: Context) {
        showProgressSetVisivility()
        interactor.getSubMenuTournaments(context)
    }

    override fun saveTournament(context: Context, Tournament: Tournament) {
        showProgressSetVisivility()
        interactor.saveTournament(context, Tournament)
    }

    override fun updateTournament(context: Context, Tournament: Tournament) {
        showProgressSetVisivility()
        interactor.updateTournament(context, Tournament)
    }

    override fun activeOrUnActiveTournament(context: Context, tournament: Tournament) {
        showProgressSetVisivility()
        interactor.activeOrUnActiveTournament(context, tournament)
    }

    override fun deleteTournament(context: Context, Tournament: Tournament) {
        showProgressSetVisivility()
        interactor.deleteTournament(context, Tournament)
    }

    override fun assignationTournament(context: Context, subMenu: SubMenuDrawer, tounament: Int) {
        showProgressSetVisivility()
        interactor.assignationTournament(context, subMenu, tounament)
    }

    override fun getTournamentForSubMenu(context: Context, id_submenu: Int) {
        showProgressSetVisivility()
        interactor.getTournamentForSubMenu(context, id_submenu)
    }

    private fun showProgressSetVisivility() {
        view.showProgressBar()
        view.setVisibilityViews(View.INVISIBLE)
    }

    private fun hideProgressSetVisivility() {
        view.hideProgressBar()
        view.setVisibilityViews(View.VISIBLE)
    }

    @Subscribe
    override fun onEventMainThread(event: TournamentActivityEvent) {
        when (event.type) {
            TournamentActivityEvent.GETSUBMENUSTORNAMENTS -> {
                view.setSubMenusTournaments(event.tournaments!!, event.subMenuDrawers!!)
                hideProgressSetVisivility()
            }

            TournamentActivityEvent.EVENTTORNAMENTSUCCESS -> {
                view.refreshData()
                view.cleanViews()
                view.eventSuccess(event.msg!!)
            }

            TournamentActivityEvent.EVENTASSIGNATIONSUCCESS -> {
                hideProgressSetVisivility()
                view.assignationSuccess()
            }

            TournamentActivityEvent.GETTORNAMENTFORSUBMENU-> {
                view.setTournamentForSubMenu(event.id)
                hideProgressSetVisivility()
            }

            TournamentActivityEvent.ERROR -> {
                hideProgressSetVisivility()
                view.setError(event.msg!!)
            }
        }
    }
}