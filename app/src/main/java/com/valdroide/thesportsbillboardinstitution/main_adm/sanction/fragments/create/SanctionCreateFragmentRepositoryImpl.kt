package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.events.SanctionCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.helper.DateTimeHelper

class SanctionCreateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : SanctionCreateFragmentRepository {

    private var sanction: Sanction? = null
    private var response: WSResponse? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null
    private var players: MutableList<Player>? = null

    override fun getSanction(context: Context, id_sanction: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getSanction(id_sanction)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    sanction = result.sanction
                                    post(SanctionCreateFragmentEvent.GETSANCTION, sanction!!)
                                } else {
                                    post(SanctionCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun getPlayerForIdSubMenu(context: Context, id_submenu: Int){
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
                                    post(SanctionCreateFragmentEvent.GETPLAYERFORID, players)
                                } else {
                                    post(SanctionCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionCreateFragmentEvent.ERROR, e.message)
        }
    }

    private fun getOficialDate(): String = DateTimeHelper.getFechaOficialSeparate()

    override fun saveSanction(context: Context, sanction: Sanction) {
        try {
            apiService.saveSanction(sanction.YELLOW, sanction.RED, sanction.SANCTION,
                    sanction.OBSERVATION, sanction.ID_SUB_MENU, sanction.ID_PLAYER, 1,
                    getOficialDate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(SanctionCreateFragmentEvent.SAVESANCTION)
                                } else {
                                    post(SanctionCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun updateSanction(context: Context, sanction: Sanction) {
        try {
            apiService.updateSanction(sanction.ID_SANCTION_KEY, sanction.YELLOW, sanction.RED, sanction.SANCTION,
                    sanction.OBSERVATION, sanction.ID_SUB_MENU, sanction.ID_PLAYER, 1,
                    getOficialDate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(SanctionCreateFragmentEvent.UPDATESANCTION)
                                } else {
                                    post(SanctionCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun getSubMenusAndPlayers(context: Context) {
        try {
            apiService.getSubMenusAndPlayers()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    subMenus = result.subMenuDrawer
                                    players = result.players
                                    post(SanctionCreateFragmentEvent.GETSUBMENUPLAYER, subMenus, players)
                                } else {
                                    post(SanctionCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionCreateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, null, null, error)
    }

    fun post(types: Int, sanction: Sanction) {
        post(types, sanction, null, null, null)
    }

    fun post(types: Int, submenus: MutableList<SubMenuDrawer>?, players: MutableList<Player>?) {
        post(types, null, submenus, players, null)
    }

    fun post(types: Int, players: MutableList<Player>?) {
        post(types, null, null, players, null)
    }

    fun post(types: Int, Sanction: Sanction?, submenus: MutableList<SubMenuDrawer>?, players: MutableList<Player>?, error: String?) {
        val event = SanctionCreateFragmentEvent()
        event.type = types
        event.sanction = Sanction
        event.subMenuDrawers = submenus
        event.players = players
        event.error = error
        eventBus.post(event)
    }
}