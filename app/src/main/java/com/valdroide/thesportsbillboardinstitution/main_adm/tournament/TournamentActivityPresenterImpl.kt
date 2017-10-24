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
        interactor.getSubMenuTournaments(context)
    }

    override fun getSubMenuForId(context: Context, id_tournament: Tournament) {
        interactor.getSubMenuForId(context, id_tournament)
    }

    override fun saveTournament(context: Context, Tournament: Tournament) {
        interactor.saveTournament(context, Tournament)
    }

    override fun updateTournament(context: Context, Tournament: Tournament) {
        interactor.updateTournament(context, Tournament)
    }

    override fun activeOrUnActiveTournament(context: Context, tournament: Tournament) {
        interactor.activeOrUnActiveTournament(context, tournament)
    }

    override fun deleteTournament(context: Context, Tournament: Tournament) {
        interactor.deleteTournament(context, Tournament)
    }

    override fun assignationUnassignation(context: Context, subMenu: SubMenuDrawer, tounament: Int, isActual: Boolean) {
        interactor.assignationUnassignation(context, subMenu, tounament, isActual)
    }

    @Subscribe
    override fun onEventMainThread(event: TournamentActivityEvent) {
        when (event.type) {
            TournamentActivityEvent.GETSUBMENUSTORNAMENTS -> {
                view.setSubMenusTournaments(event.tournaments!!)
                view.hideProgressBar()
                view.setVisibilityViews(View.VISIBLE)
            }

            TournamentActivityEvent.GETSUBMENUSFORIDTOURNAMENTS-> {
                view.setSubMenusForTournament(event.subMenuDrawers!!)
                view.hideProgressBar()
                view.setVisibilityViews(View.VISIBLE)
            }

            TournamentActivityEvent.EVENTTORNAMENTSUCCESS -> {
                view.refreshRecyclerAndSpinner()
                view.cleanViews()
                view.eventSuccess(event.msg!!)
            }

            TournamentActivityEvent.EVENTASSIGNATIONSUCCESS -> {
                view.hideProgressBar()
                view.setVisibilityViews(View.VISIBLE)
                view.assignationSuccess(event.subMenuDrawer!!, event.isActual)
            }

            TournamentActivityEvent.ERROR -> {
                view.hideProgressBar()
                view.setVisibilityViews(View.VISIBLE)
                view.setError(event.msg!!)
            }
        }
    }
}