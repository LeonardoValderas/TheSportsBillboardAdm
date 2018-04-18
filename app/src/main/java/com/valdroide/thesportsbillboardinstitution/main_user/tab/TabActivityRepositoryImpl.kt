package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events.TournamentActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.tab.events.TabActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class TabActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : TabActivityRepository {

    private var response: WSResponse? = null

    override fun getTournamentoForIdSubmenu(context: Context, id_menu: Int) {
        try {
            apiService.getTournamentsForIdSubMenu(id_menu)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if(result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(TabActivityEvent.GET_TOURNAMENT, result.tournaments)
                                } else {
                                    post(TabActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(TabActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(TabActivityEvent.ERROR, context.getString(R.string.null_response))
                        }
                    }, { e ->
                        post(TabActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(TabActivityEvent.ERROR, e.message!!)
        }
    }

    fun post(types: Int, msg: String?) {
        post(types, null, msg)
    }

    fun post(types: Int, tournaments: MutableList<Tournament>?) {
        post(types, tournaments, null)
    }

    fun post(types: Int,
             tournaments: MutableList<Tournament>?,
             msg: String?) {

        val event = TabActivityEvent()
        event.type = types
        event.tournaments = tournaments
        event.error = msg
        eventBus.post(event)
    }
}
