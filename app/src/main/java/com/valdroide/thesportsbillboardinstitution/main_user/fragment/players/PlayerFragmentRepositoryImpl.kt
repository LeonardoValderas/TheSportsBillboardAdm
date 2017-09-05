package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.events.PlayerFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class PlayerFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : PlayerFragmentRepository {
    private var players: MutableList<Player>? = null
    private var response: WSResponse? = null

    override fun getPlayers(context: Context, id_submenu: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getPlayers(id_submenu)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    players = result.players
                                    post(PlayerFragmentEvent.PLAYERS, players!!)

                                } else {
                                    post(PlayerFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(PlayerFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(PlayerFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(PlayerFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(PlayerFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, players: MutableList<Player>) {
        post(types, players, null)
    }

    fun post(types: Int, players: MutableList<Player>?, error: String?) {
        val event = PlayerFragmentEvent()
        event.type = types
        event.players = players
        event.error = error
        eventBus.post(event)
    }
}