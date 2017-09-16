package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.events.PlayerCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class PlayerCreateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : PlayerCreateFragmentRepository {

    private var player: Player? = null
    private var response: WSResponse? = null
    private var positions: MutableList<Position>? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null

    override fun getPlayer(context: Context, id_player: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getPlayer(id_player)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    player = result.player
                                    post(PlayerCreateFragmentEvent.GETPLAYER, player!!)
                                } else {
                                    post(PlayerCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(PlayerCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(PlayerCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun savePlayer(context: Context, player: Player) {
        try {
            apiService.savePlayer(player.NAME, player.ID_POSITION, player.ID_SUB_MENU,
                                  player.URL_IMAGE, player.NAME_IMAGE, player.ENCODE, 1,
                                  Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(PlayerCreateFragmentEvent.SAVEPLAYER)
                                } else {
                                    post(PlayerCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(PlayerCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(PlayerCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun updatePlayer(context: Context, player: Player) {
        try {
            apiService.updatePlayer(player.ID_PLAYER_KEY, player.NAME, player.ID_POSITION, player.ID_SUB_MENU, player.URL_IMAGE, player.NAME_IMAGE, player.ENCODE, player.BEFORE,
                    player.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(PlayerCreateFragmentEvent.UPDATEPLAYER)
                                } else {
                                    post(PlayerCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(PlayerCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(PlayerCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun getPositionsSubMenus(context: Context) {
        try {
            apiService.getPositionsSubMenus()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    positions = result.positions
                                    subMenus = result.submunes
                                    post(PlayerCreateFragmentEvent.POSITIONSSUBMENUS, positions, subMenus)
                                } else {
                                    post(PlayerCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(PlayerCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(PlayerCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(PlayerCreateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, null, null, error)
    }

    fun post(types: Int, player: Player) {
        post(types, player, null, null, null)
    }

    fun post(types: Int, positions: MutableList<Position>?, submenus: MutableList<SubMenuDrawer>?) {
        post(types, null, positions, submenus, null)
    }

    fun post(types: Int, player: Player?, positions: MutableList<Position>?, submenus: MutableList<SubMenuDrawer>?, error: String?) {
        val event = PlayerCreateFragmentEvent()
        event.type = types
        event.player = player
        event.positions = positions
        event.submenus = submenus
        event.error = error
        eventBus.post(event)
    }
}