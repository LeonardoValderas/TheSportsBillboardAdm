package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.events.LoginEditFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.helper.DateTimeHelper

class LoginEditFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : LoginEditFragmentRepository {

    private var logins: MutableList<Login>? = null
    private var response: WSResponse? = null

    override fun getLogins(context: Context) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getLogins()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    logins = result.logins
                                    post(LoginEditFragmentEvent.LOGINS, logins!!)
                                } else {
                                    post(LoginEditFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(LoginEditFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(LoginEditFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LoginEditFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LoginEditFragmentEvent.ERROR, e.message)
        }
    }

    override fun activeOrUnActiveLogins(context: Context, login: Login) {
        try {
            apiService.activeOrUnActiveLogin(login.ID_LOGIN_KEY, login.IS_ACTIVE, 1, DateTimeHelper.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(LoginEditFragmentEvent.EDIT)
                                } else {
                                    post(LoginEditFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(LoginEditFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(LoginEditFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LoginEditFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LoginEditFragmentEvent.ERROR, e.message)
        }
    }

    override fun deleteLogin(context: Context, login: Login) {
        try {
            apiService.deleteLogin(login.ID_LOGIN_KEY, 1, DateTimeHelper.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(LoginEditFragmentEvent.DELETE)
                                } else {
                                    post(LoginEditFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(LoginEditFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(LoginEditFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LoginEditFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LoginEditFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, logins: MutableList<Login>) {
        post(types, logins, null)
    }

    fun post(types: Int, logins: MutableList<Login>?, error: String?) {
        val event = LoginEditFragmentEvent()
        event.type = types
        event.logins = logins
        event.error = error
        eventBus.post(event)
    }
}