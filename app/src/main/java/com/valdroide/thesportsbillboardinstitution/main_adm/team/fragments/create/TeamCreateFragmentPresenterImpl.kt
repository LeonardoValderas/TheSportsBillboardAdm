package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create

import android.content.Context
import android.view.View
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.events.TeamCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui.TeamCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import org.greenrobot.eventbus.Subscribe

class TeamCreateFragmentPresenterImpl(var view: TeamCreateFragmentView, val event: EventBus, val interactor: TeamCreateFragmentInteractor) : TeamCreateFragmentPresenter {

    override fun getViewPresenter(): TeamCreateFragmentView = view

    override fun setViewPresenter(view: TeamCreateFragmentView) {
        this.view = view
    }

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getTeam(context: Context, id_team: Int) {
        showProgressAndSetVisibility()
        interactor.getTeam(context, id_team)
    }

    override fun saveTeam(context: Context, team: Team) {
        showProgressAndSetVisibility()
        interactor.saveTeam(context, team)
    }

    override fun updateTeam(context: Context, team: Team) {
        showProgressAndSetVisibility()
        interactor.updateTeam(context, team)
    }

    private fun showProgressAndSetVisibility() {
        view.showProgressDialog()
        view.setVisibilityViews(View.INVISIBLE)
    }

    private fun hideProgressAndSetVisibility() {
        view.hideProgressDialog()
        view.setVisibilityViews(View.VISIBLE)
    }

    @Subscribe
    override fun onEventMainThread(event: TeamCreateFragmentEvent) {
        when (event.type) {
            TeamCreateFragmentEvent.GETTEAM -> {
                hideProgressAndSetVisibility()
                view.setTeamEdit(event.team!!)
                view.fillViewUpdate()
            }
            TeamCreateFragmentEvent.SAVETEAM -> {
                hideProgressAndSetVisibility()
                view.saveSuccess()
                view.cleanViews()
            }
            TeamCreateFragmentEvent.UPDATETEAM -> {
                hideProgressAndSetVisibility()
                view.editSuccess()
                view.cleanViews()
            }
            TeamCreateFragmentEvent.ERROR -> {
                hideProgressAndSetVisibility()
                view.setError(event.error!!)
            }
        }
    }
}