package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.LoginEditFragmentInteractor
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.LoginEditFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.events.LoginEditFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.events.TeamUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import org.greenrobot.eventbus.Subscribe

class TeamUpdateFragmentPresenterImpl(var view: TeamUpdateFragmentView, val event: EventBus, val interactor: TeamUpdateFragmentInteractor) : TeamUpdateFragmentPresenter {

    override fun getViewPresenter(): TeamUpdateFragmentView = view

    override fun setViewPresenter(view: TeamUpdateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getTeams(context: Context) {
        view.showSwipeRefreshLayout()
        interactor.getTeams(context)
    }

    override fun activeUnActiveTeam(context: Context, team: Team) {
        view.showSwipeRefreshLayout()
        interactor.activeUnActiveTeam(context, team)
    }

    override fun deleteTeam(context: Context, team: Team) {
        view.showSwipeRefreshLayout()
        interactor.deleteTeam(context, team)
    }

    @Subscribe
    override fun onEventMainThread(event: TeamUpdateFragmentEvent) {
        when (event.type) {
            TeamUpdateFragmentEvent.TEAMS -> {
                view.hideSwipeRefreshLayout()
                view.setAllTeams(event.teams!!)
            }
            TeamUpdateFragmentEvent.UPDATE -> {
                view.hideSwipeRefreshLayout()
                view.updateTeamSuccess()
            }
            TeamUpdateFragmentEvent.DELETE -> {
                view.hideSwipeRefreshLayout()
                view.deleteTeamSuccess()
            }
            TeamUpdateFragmentEvent.ERROR -> {
                view.hideSwipeRefreshLayout()
                view.setError(event.error!!)
            }
        }
    }
}