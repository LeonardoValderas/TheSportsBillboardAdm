package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.events.SanctionUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.helper.DateTimeHelper

class SanctionUpdateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : SanctionUpdateFragmentRepository {

    private var sanctions: MutableList<Sanction>? = null
    private var response: WSResponse? = null

    override fun getSanction(context: Context) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getSanctions()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    sanctions = result.sanctions
                                    post(SanctionUpdateFragmentEvent.SANCTIONS, sanctions!!)
                                } else {
                                    post(SanctionUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionUpdateFragmentEvent.ERROR, e.message)
        }
    }

    override fun deleteSanction(context: Context, sanction: Sanction) {
        try {
            apiService.deleteSanction(sanction.ID_SANCTION_KEY, 1, DateTimeHelper.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(SanctionUpdateFragmentEvent.DELETE)
                                } else {
                                    post(SanctionUpdateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(SanctionUpdateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(SanctionUpdateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(SanctionUpdateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(SanctionUpdateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, sanctions: MutableList<Sanction>) {
        post(types, sanctions, null)
    }

    fun post(types: Int, sanctions: MutableList<Sanction>?, error: String?) {
        val event = SanctionUpdateFragmentEvent()
        event.type = types
        event.sanctions = sanctions
        event.error = error
        eventBus.post(event)
    }
}