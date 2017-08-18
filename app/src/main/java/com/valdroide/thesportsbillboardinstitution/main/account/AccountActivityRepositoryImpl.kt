package com.valdroide.thesportsbillboardinstitution.main.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.account.events.AccountActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.response.AccountResponse
import com.valdroide.thesportsbillboardinstitution.model.response.WSResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AccountActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService) : AccountActivityRepository {
    var account: Account? = null
    var response: WSResponse? = null
    override fun getAccount(context: Context) {
        try {
            apiService.getAccount()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null)
                                if (response?.SUCCESS.equals("0")) {
                                    account = result.account
                                    post(AccountActivityEvent.ACCOUNT, account!!)
                                } else {
                                    post(AccountActivityEvent.ERROR, response?.MESSAGE)
                                }
                        } else {
                            post(AccountActivityEvent.ERROR, "Respuesta nula.")
                        }
                    }, { e -> post(AccountActivityEvent.ERROR, e.message) })
        } catch (e: Exception) {
            post(AccountActivityEvent.ERROR, e.message)
        }
    }

    fun getAccount(): Observable<AccountResponse>? {
        return apiService.getAccount()
                .flatMap { data -> Observable.just(data) }
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, account: Account) {
        post(types, account, null)
    }

    fun post(types: Int, account: Account?, error: String?) {
        val event = AccountActivityEvent()
        event.type = types
        event.account = account
        event.error = error
        eventBus.post(event)
    }
}