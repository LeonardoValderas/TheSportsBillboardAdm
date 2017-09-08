package com.valdroide.thesportsbillboardinstitution.main_adm.account

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Account

interface AccountAdmActivityInteractor {
    fun getAccount(context: Context)
    fun saveAccount(context: Context, account: Account)
}