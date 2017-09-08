package com.valdroide.thesportsbillboardinstitution.main_adm.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.account.events.AccountAdmActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account

interface AccountAdmActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getAccount(context: Context)
    fun saveAccount(context: Context, account: Account)
    fun onEventMainThread(event: AccountAdmActivityEvent)
}