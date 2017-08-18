package com.valdroide.thesportsbillboardinstitution.main.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main.account.events.AccountActivityEvent

interface AccountActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getAccount(context: Context)
    fun onEventMainThread(event: AccountActivityEvent)
}