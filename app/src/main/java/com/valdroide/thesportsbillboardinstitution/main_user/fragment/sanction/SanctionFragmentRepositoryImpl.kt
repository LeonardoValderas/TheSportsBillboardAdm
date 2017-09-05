package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.events.SanctionFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

class SanctionFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : SanctionFragmentRepository {
    private var sanction: MutableList<Sanction>? = null
    private var response: WSResponse? = null

    override fun getSanctions(context: Context, id_submenu: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getSanctions(id_submenu)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    sanction = result.sanctions
                                    post(SanctionFragmentEvent.SANCTION, sanction!!)

                                } else {
                                    post(SanctionFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, sanctions: MutableList<Sanction>) {
        post(types, sanctions, null)
    }

    fun post(types: Int, sanctions: MutableList<Sanction>?, error: String?) {
        val event = SanctionFragmentEvent()
        event.type = types
        event.sanctions = sanctions
        event.error = error
        eventBus.post(event)
    }
}