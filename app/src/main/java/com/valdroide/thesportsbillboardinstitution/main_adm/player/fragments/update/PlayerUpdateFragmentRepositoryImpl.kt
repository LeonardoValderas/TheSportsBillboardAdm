package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.events.PlayerUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events.TournamentActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.helper.DateTimeHelper

class PlayerUpdateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : PlayerUpdateFragmentRepository {

    private var players: MutableList<Player>? = null
    private var response: WSResponse? = null
    private var id_user = 1

    override fun getPlayers(context: Context) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getPlayers()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    players = result.players
                                    post(PlayerUpdateFragmentEvent.PLAYERS, players!!)
                                } else {
                                    post(PlayerUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(PlayerUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(PlayerUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(PlayerUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(PlayerUpdateFragmentEvent.ERROR, e.message)
        }
    }

    override fun activeUnActivePlayer(context: Context, player: Player) {
        if (id_user != 0) {
            try {
                apiService.activeOrUnActivePlayer(player.ID_PLAYER_KEY, player.IS_ACTIVE, 1, getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(PlayerUpdateFragmentEvent.UPDATE)
                                    } else {
                                        post(PlayerUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                    }
                                } else {
                                    post(PlayerUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                                }
                            } else {
                                post(PlayerUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                            }
                        }, { e ->
                            post(PlayerUpdateFragmentEvent.ERROR, e.message)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(PlayerUpdateFragmentEvent.ERROR, e.message)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    private fun getOficialDate(): String = DateTimeHelper.getFechaOficialSeparate()

    override fun deletePlayer(context: Context, player: Player) {
        if (id_user != 0) {
            try {
                apiService.deletePlayer(player.ID_PLAYER_KEY, 1, getOficialDate())
                        .subscribeOn(scheduler.schedulerIO())
                        .observeOn(scheduler.schedulerMainThreader())
                        .subscribe({ result ->
                            if (result != null) {
                                response = result
                                if (response != null) {
                                    if (response?.SUCCESS.equals("0")) {
                                        post(PlayerUpdateFragmentEvent.DELETE)
                                    } else {
                                        post(PlayerUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                    }
                                } else {
                                    post(PlayerUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                                }
                            } else {
                                post(PlayerUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                            }
                        }, { e ->
                            post(PlayerUpdateFragmentEvent.ERROR, e.message)
                            FirebaseCrash.report(e)
                        })
            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(PlayerUpdateFragmentEvent.ERROR, e.message)
            }
        } else {
            post(TournamentActivityEvent.ERROR, context.getString(R.string.error_id_user_zero))
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, Players: MutableList<Player>) {
        post(types, Players, null)
    }

    fun post(types: Int, players: MutableList<Player>?, error: String?) {
        val event = PlayerUpdateFragmentEvent()
        event.type = types
        event.players = players
        event.error = error
        eventBus.post(event)
    }
}