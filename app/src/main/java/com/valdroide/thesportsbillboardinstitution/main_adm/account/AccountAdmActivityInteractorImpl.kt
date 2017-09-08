package com.valdroide.thesportsbillboardinstitution.main_adm.account

import android.content.Context
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.valdroide.thesportsbillboardinstitution.model.entities.Account

class AccountAdmActivityInteractorImpl(val repository: AccountAdmActivityRepository) : AccountAdmActivityInteractor {

    override fun getAccount(context: Context) {
        repository.getAccount(context)
    }

    override fun saveAccount(context: Context, account: Account) {
        repository.saveAccount(context, account)
    }
}