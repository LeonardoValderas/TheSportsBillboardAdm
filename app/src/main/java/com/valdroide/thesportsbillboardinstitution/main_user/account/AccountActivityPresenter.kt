package com.valdroide.thesportsbillboardinstitution.main_user.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.account.events.AccountActivityEvent

interface AccountActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getAccount(context: Context)
    fun onEventMainThread(event: AccountActivityEvent)
}