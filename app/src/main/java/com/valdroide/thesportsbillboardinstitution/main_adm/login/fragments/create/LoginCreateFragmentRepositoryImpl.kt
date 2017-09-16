package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.events.LoginCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.FixtureFragmentRepository
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.events.FixtureFragmentEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class LoginCreateFragmentRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : LoginCreateFragmentRepository {


    private var login: Login? = null
    private var response: WSResponse? = null

    override fun getLogin(context: Context, id_login: Int) {
        //internet connection
        // validate id_submenu different to zero
        try {
            apiService.getLogin(id_login)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    login = result.login
                                    post(LoginCreateFragmentEvent.GETLOGIN, login!!)
                                } else {
                                    post(LoginCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(LoginCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(LoginCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LoginCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LoginCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun saveLogin(context: Context, login: Login) {
        try {
            apiService.saveLogin(login.USER, login.PASS, login.TYPE_ADM, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(LoginCreateFragmentEvent.SAVELOGIN)
                                } else {
                                    post(LoginCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(LoginCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(LoginCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LoginCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LoginCreateFragmentEvent.ERROR, e.message)
        }
    }

    override fun editLogin(context: Context, login: Login) {
        try {
            apiService.editLogin(login.ID_LOGIN_KEY, login.USER, login.PASS, login.TYPE_ADM, login.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(LoginCreateFragmentEvent.EDITLOGIN)
                                } else {
                                    post(LoginCreateFragmentEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(LoginCreateFragmentEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(LoginCreateFragmentEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(LoginCreateFragmentEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(LoginCreateFragmentEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }
    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, login: Login) {
        post(types, login, null)
    }

    fun post(types: Int, login: Login?, error: String?) {
        val event = LoginCreateFragmentEvent()
        event.type = types
        event.login = login
        event.error = error
        eventBus.post(event)
    }
}