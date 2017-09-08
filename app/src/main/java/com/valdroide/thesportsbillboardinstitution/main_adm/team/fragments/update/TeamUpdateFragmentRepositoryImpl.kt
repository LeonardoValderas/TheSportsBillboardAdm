package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.events.TeamUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class TeamUpdateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : TeamUpdateFragmentRepository {

    private var teams: MutableList<Team>? = null
    private var response: WSResponse? = null

    override fun getTeams(context: Context) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getTeams()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    teams = result.teams
                                    post(TeamUpdateFragmentEvent.TEAMS, teams!!)
                                } else {
                                    post(TeamUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(TeamUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TeamUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TeamUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TeamUpdateFragmentEvent.ERROR, e.message)
        }
    }

    override fun activeUnActiveTeam(context: Context, team: Team) {
        try {
            apiService.activeOrUnActiveTeam(team.ID_TEAM_KEY, team.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TeamUpdateFragmentEvent.UPDATE)
                                } else {
                                    post(TeamUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(TeamUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TeamUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TeamUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TeamUpdateFragmentEvent.ERROR, e.message)
        }
    }


    override fun deleteTeam(context: Context, team: Team) {
        try {
            apiService.deleteTeam(team.ID_TEAM_KEY, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TeamUpdateFragmentEvent.DELETE)
                                } else {
                                    post(TeamUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(TeamUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TeamUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TeamUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TeamUpdateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, teams: MutableList<Team>) {
        post(types, teams, null)
    }

    fun post(types: Int, teams: MutableList<Team>?, error: String?) {
        val event = TeamUpdateFragmentEvent()
        event.type = types
        event.teams = teams
        event.error = error
        eventBus.post(event)
    }
}