package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.events.TeamCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events.TournamentActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class TournamentActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : TournamentActivityRepository {

    private var response: WSResponse? = null
    private var tournaments: MutableList<Tournament>? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null
    private var id_user = 1

    override fun getSubMenuTournaments(context: Context) {
        try {
            apiService.getSubMenus()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    tournaments = result.tournaments
                                    post(TournamentActivityEvent.GETSUBMENUSTORNAMENTS, tournaments, true)
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

    override fun getSubMenuForId(context: Context, id_tournament: Tournament) {
        try {
            apiService.getSubMenuForTournament(id_tournament.ID_TOURNAMENT_KEY)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    subMenus = result.subMenus
                                    post(TournamentActivityEvent.GETSUBMENUSFORIDTOURNAMENTS, subMenus)
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

    override fun saveTournament(context: Context, tournament: Tournament) {
        if (id_user != 0) {
            try {
                apiService.saveTournament(tournament.TOURNAMENT, id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.save_success, "Torneo"))
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
                        id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.update_success, "Torneo"))
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
                        id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.update_success, "Torneo"))
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
                apiService.deleteTournament(tournament.ID_TOURNAMENT_KEY, id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(TournamentActivityEvent.EVENTTORNAMENTSUCCESS, context.getString(R.string.delete_success, "Torneo"))
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

    override fun assignationUnassignation(context: Context, subMenu: SubMenuDrawer, tounament: Int, isActual: Boolean) {
        if (id_user != 0) {
            try {
                apiService.assignationUnassignation(subMenu.ID_SUBMENU_KEY, tounament,
                        subMenu.ID_TOURNAMENT_SUBMENU, isActual, id_user, Utils.getFechaOficialSeparate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    if (result.ID != 0)
                                        subMenu.ID_TOURNAMENT_SUBMENU = result.ID

                                    if (isActual) subMenu.IS_CHECKED = 1 else subMenu.IS_CHECKED = 0
                                    post(TournamentActivityEvent.EVENTASSIGNATIONSUCCESS, subMenu, isActual)
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
        post(types, null, null, null, null, false)
    }

    fun post(types: Int, msg: String?) {
        post(types, null, null, null, msg, false)
    }

    fun post(types: Int, tournaments: MutableList<Tournament>?, isTournament: Boolean) {
        post(types, null, null, tournaments, null, false)
    }

    fun post(types: Int, subMenus: MutableList<SubMenuDrawer>?) {
        post(types, null, subMenus, null, null, false)
    }

    fun post(types: Int, subMenu: SubMenuDrawer?, isActual: Boolean) {
        post(types, subMenu, null, null, null, isActual)
    }

    fun post(types: Int,
             menuDrawer: SubMenuDrawer?,
             memuDrawers: MutableList<SubMenuDrawer>?,
             tournaments: MutableList<Tournament>?,
             msg: String?, isActual: Boolean) {
        val event = TournamentActivityEvent()
        event.type = types
        event.subMenuDrawer = menuDrawer
        event.subMenuDrawers = memuDrawers
        event.tournaments = tournaments
        event.msg = msg
        event.isActual = isActual
        eventBus.post(event)
    }
}