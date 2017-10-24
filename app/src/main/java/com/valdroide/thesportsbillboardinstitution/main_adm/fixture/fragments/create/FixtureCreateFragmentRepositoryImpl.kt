package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.events.FixtureCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.events.TeamCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class FixtureCreateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : FixtureCreateFragmentRepository {

    private var fixture: Fixture? = null
    private var response: WSResponse? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null
    private var fieldMatchs: MutableList<FieldMatch>? = null
    private var timeMatchs: MutableList<TimeMatch>? = null
    private var tournaments: MutableList<Tournament>? = null
    private var teams: MutableList<Team>? = null
    private var id_user = 1

    override fun getFixture(context: Context, id_fixture: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getFixture(id_fixture)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    fixture = result.fixture
                                    post(FixtureCreateFragmentEvent.GETFIXTURE, fixture!!)
                                } else {
                                    post(FixtureCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(FixtureCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(FixtureCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun getPlayerForIdSubMenu(context: Context, id_submenu: Int) {
/*
        try {
            apiService.getPlayerForIdSubMenu(id_submenu)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    players = result.players
                                    post(FixtureCreateFragmentEvent.GETPLAYERFORID, players)
                                } else {
                                    post(FixtureCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(FixtureCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(FixtureCreateFragmentEvent.ERROR, e.message)
        }
        */
    }

    override fun saveFixture(context: Context, fixture: Fixture) {
        if (id_user != 0) {
            try {
                apiService.saveFixture(fixture.ID_TOURNAMENT,
                        fixture.ID_LOCAL_TEAM, fixture.ID_VISITE_TEAM,
                        fixture.ID_SUBMENU_KEY, fixture.ID_FIELD_MATCH,
                        fixture.ID_TIMES_MATCH, fixture.RESULT_LOCAL, fixture.RESULT_VISITE, fixture.DATE_MATCH,
                        fixture.HOUR_MATCH, fixture.OBSERVATION, id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(FixtureCreateFragmentEvent.SAVEFIXTURE)
                                    } else {
                                        post(FixtureCreateFragmentEvent.ERROR, response?.MESSAGE)
                                    }
                                } else {
                                    post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                                }
                            } else {
                                post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                            }
                        }, { e ->
                            post(FixtureCreateFragmentEvent.ERROR, e.message)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(FixtureCreateFragmentEvent.ERROR, e.message)
            }
        } else {
            post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }

    }

    override fun updateFixture(context: Context, fixture: Fixture) {
        if (id_user != 0) {
            try {
                apiService.updateFixture(fixture.ID_FIXTURE_KEY, fixture.ID_TOURNAMENT,
                        fixture.ID_LOCAL_TEAM, fixture.ID_VISITE_TEAM,
                        fixture.ID_SUBMENU_KEY, fixture.ID_FIELD_MATCH,
                        fixture.ID_TIMES_MATCH, fixture.RESULT_LOCAL, fixture.RESULT_VISITE, fixture.DATE_MATCH,
                        fixture.HOUR_MATCH, fixture.OBSERVATION, id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(FixtureCreateFragmentEvent.UPDATEFIXTURE)
                                    } else {
                                        post(FixtureCreateFragmentEvent.ERROR, response?.MESSAGE)
                                    }
                                } else {
                                    post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                                }
                            } else {
                                post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                            }
                        }, { e ->
                            post(FixtureCreateFragmentEvent.ERROR, e.message)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(FixtureCreateFragmentEvent.ERROR, e.message)
            }
        } else {
            post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun getSpinnerData(context: Context) {
        try {
            apiService.getSpinnersData()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    subMenus = result.subMenus
                                    fieldMatchs = result.fieldMatchs
                                    timeMatchs = result.timeMatchs
                                    tournaments = result.tournament
                                    teams = result.teams
                                    post(FixtureCreateFragmentEvent.GETSPINNERSDATA, subMenus, fieldMatchs, timeMatchs, tournaments, teams)
                                } else {
                                    post(FixtureCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(FixtureCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(FixtureCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(FixtureCreateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null, null, null, null, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, null, null, null, null, null, error)
    }

    fun post(types: Int, Fixture: Fixture) {
        post(types, Fixture, null, null, null, null, null, null)
    }

    fun post(types: Int, submenus: MutableList<SubMenuDrawer>?, fieldMatchs: MutableList<FieldMatch>?,
             timeMatchs: MutableList<TimeMatch>?, tournaments: MutableList<Tournament>?, teams: MutableList<Team>?) {
        post(types, null, submenus, fieldMatchs, timeMatchs, tournaments, teams, null)
    }

    fun post(types: Int, Fixture: Fixture?, submenus: MutableList<SubMenuDrawer>?,
             fieldMatchs: MutableList<FieldMatch>?, timeMatchs: MutableList<TimeMatch>?,
             tournaments: MutableList<Tournament>?, teams: MutableList<Team>?
             , error: String?) {
        val event = FixtureCreateFragmentEvent()
        event.type = types
        event.fixture = Fixture
        event.subMenuDrawers = submenus
        event.fieldMatchs = fieldMatchs
        event.timeMatchs = timeMatchs
        event.tournaments = tournaments
        event.teams = teams
        event.error = error
        eventBus.post(event)
    }
}