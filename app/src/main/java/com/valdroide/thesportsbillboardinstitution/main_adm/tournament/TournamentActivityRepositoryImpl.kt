package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events.TournamentActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.helper.DateTimeHelper

class TournamentActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : TournamentActivityRepository {

    private var response: WSResponse? = null
    private var tournaments: MutableList<Tournament>? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null
    private var id_user = 1

    override fun getSubMenuTournaments(context: Context) {
        try {
            apiService.getTournamentsAndSubMenus()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    tournaments = result.tournaments
                                    subMenus = result.subMenus
                                    post(TournamentActivityEvent.GETSUBMENUSTORNAMENTS, tournaments, subMenus)
                                } else {
                                    post(TournamentActivityEvent.ERROR, response!!.MESSAGE)
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(
                                        R.string.null_response))
                            }
                        } else {
                            post(TournamentActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(TournamentActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TournamentActivityEvent.ERROR, e.message!!)
        }
    }

    private fun getOficialDate(): String = DateTimeHelper.getFechaOficialSeparate()

    override fun saveTournament(context: Context, tournament: Tournament) {
        if (id_user != 0) {
            try {
                apiService.saveTournament(tournament.TOURNAMENT, id_user, getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.save_success, "Torneo", "o"))
                                } else {
                                    post(TournamentActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        }, { e ->
                            post(TournamentActivityEvent.ERROR, e.message!!)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(TournamentActivityEvent.ERROR, e.message!!)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun updateTournament(context: Context, tournament: Tournament) {
        if (id_user != 0) {
            try {
                apiService.updateTournament(tournament.ID_TOURNAMENT_KEY, tournament.TOURNAMENT, tournament.IS_ACTIVE,
                        id_user, getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.update_success, "Torneo", "o"))
                                } else {
                                    post(TournamentActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        }, { e ->
                            post(TournamentActivityEvent.ERROR, e.message!!)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(TournamentActivityEvent.ERROR, e.message!!)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun activeOrUnActiveTournament(context: Context, tournament: Tournament) {
        if (id_user != 0) {
            try {
                apiService.activeOrUnActiveTournament(tournament.ID_TOURNAMENT_KEY, tournament.IS_ACTIVE,
                        id_user, getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.update_success, "Torneo", "o"))
                                } else {
                                    post(TournamentActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        }, { e ->
                            post(TournamentActivityEvent.ERROR, e.message!!)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(TournamentActivityEvent.ERROR, e.message!!)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun deleteTournament(context: Context, tournament: Tournament) {
        if (id_user != 0) {
            try {
                apiService.deleteTournament(tournament.ID_TOURNAMENT_KEY, id_user, getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.delete_success, "Torneo", "o"))
                                    } else {
                                        post(TournamentActivityEvent.ERROR, response?.MESSAGE)
                                    }
                                } else {
                                    post(TournamentActivityEvent.ERROR, context.getString(R.string.null_response))
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(R.string.null_process))
                            }
                        }, { e ->
                            post(TournamentActivityEvent.ERROR, e.message)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(TournamentActivityEvent.ERROR, e.message)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun assignationTournament(context: Context, subMenu: SubMenuDrawer, tounament: Int) {
        if (id_user != 0) {
            try {
                apiService.assignationUnassignation(subMenu.ID_SUBMENU_KEY, tounament, id_user,
                        getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                        post(TournamentActivityEvent.EVENTASSIGNATIONSUCCESS)
                                } else {
                                    post(TournamentActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        }, { e ->
                            post(TournamentActivityEvent.ERROR, e.message!!)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(TournamentActivityEvent.ERROR, e.message!!)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    override fun getTournamentForSubMenu(context: Context, id_submenu: Int) {
        if (id_user != 0) {
            try {
                apiService.getTournamentToSubMenu(id_submenu)
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.GETTORNAMENTFORSUBMENU, response!!.ID)
                                } else {
                                    post(TournamentActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(TournamentActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        }, { e ->
                            post(TournamentActivityEvent.ERROR, e.message!!)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(TournamentActivityEvent.ERROR, e.message!!)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    fun post(types: Int) {
        post(types, null, null, 0, null)
    }

    fun post(types: Int, msg: String?) {
        post(types, null, null, 0, msg)
    }

    fun post(types: Int, tournaments: MutableList<Tournament>?, subMenus: MutableList<SubMenuDrawer>?) {
        post(types, subMenus, tournaments, 0, null)
    }

    fun post(types: Int, subMenus: MutableList<SubMenuDrawer>?) {
        post(types, subMenus, null, 0, null)
    }


    fun post(types: Int, id: Int) {
        post(types, null, null, id, null)
    }

    fun post(types: Int,
             memuDrawers: MutableList<SubMenuDrawer>?,
             tournaments: MutableList<Tournament>?, id: Int,
             msg: String?) {

        val event = TournamentActivityEvent()
        event.type = types
        event.subMenuDrawers = memuDrawers
        event.tournaments = tournaments
        event.id = id
        event.msg = msg
        eventBus.post(event)
    }
}