package com.valdroide.thesportsbillboardinstitution.main_adm.account

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.account.events.AccountAdmActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.model.response.AccountResponse

class AccountAdmActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : AccountAdmActivityRepository {
    private var account: Account? = null
    private var response: WSResponse? = null
    private var accountResponse: AccountResponse? = null

    override fun getAccount(context: Context) {
        //validate internet state connection
        try {
            apiService.getAccount()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        accountResponse = result
                        if (accountResponse != null) {
                            response = accountResponse!!.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    account = accountResponse!!.account
                                    post(AccountAdmActivityEvent.ACCOUNT, account)
                                } else {
                                    post(AccountAdmActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(AccountAdmActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(AccountAdmActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(AccountAdmActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })

        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(AccountAdmActivityEvent.ERROR, e.message)
        }
    }

    override fun saveAccount(context: Context, account: Account) {
        try {
            apiService.saveAccount(account.ID_ACCOUNT_KEY, account.DESCRIPTION, account.ADDRESS, account.PHONE,
                    account.FACEBOOK, account.INSTAGRAM, account.WEB, account.URL_IMAGE, account.NAME_IMAGE,
                    account.EMAIL, account.ENCODE, account.BEFORE, 1)
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        response = result
                        if (response != null) {
                            if (response?.SUCCESS.equals("0")) {
                                post(AccountAdmActivityEvent.SAVE)
                            } else {
                                post(AccountAdmActivityEvent.ERROR, response?.MESSAGE!!)
                            }
                        } else {
                            post(AccountAdmActivityEvent.ERROR, context.getString(R.string.null_response))
                        }

                    }, { e ->
                        post(AccountAdmActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })

        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(AccountAdmActivityEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, account: Account?) {
        post(types, account, null)
    }

    fun post(types: Int, account: Account?, error: String?) {
        val event = AccountAdmActivityEvent()
        event.type = types
        event.account = account
        event.error = error
        eventBus.post(event)
    }
}