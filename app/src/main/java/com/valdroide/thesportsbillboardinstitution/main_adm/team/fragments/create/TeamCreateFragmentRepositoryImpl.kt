package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.events.TeamCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class TeamCreateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : TeamCreateFragmentRepository {


    private var team: Team? = null
    private var response: WSResponse? = null

    override fun getTeam(context: Context, id_team: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getTeam(id_team)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    team = result.team
                                    post(TeamCreateFragmentEvent.GETTEAM, team!!)
                                } else {
                                    post(TeamCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(TeamCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TeamCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TeamCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TeamCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun saveTeam(context: Context, team: Team) {
        try {
            apiService.saveTeam(team.NAME, team.URL_IMAGE, team.NAME_IMAGE, team.ENCODE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TeamCreateFragmentEvent.SAVETEAM)
                                } else {
                                    post(TeamCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(TeamCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TeamCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TeamCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TeamCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun updateTeam(context: Context, team: Team) {
        try {
            apiService.updateTeam(team.ID_TEAM_KEY, team.NAME, team.URL_IMAGE, team.NAME_IMAGE, team.ENCODE, team.BEFORE,
                                  team.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TeamCreateFragmentEvent.UPDATETEAM)
                                } else {
                                    post(TeamCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(TeamCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TeamCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TeamCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TeamCreateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }
    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, team: Team) {
        post(types, team, null)
    }

    fun post(types: Int, team: Team?, error: String?) {
        val event = TeamCreateFragmentEvent()
        event.type = types
        event.team = team
        event.error = error
        eventBus.post(event)
    }
}