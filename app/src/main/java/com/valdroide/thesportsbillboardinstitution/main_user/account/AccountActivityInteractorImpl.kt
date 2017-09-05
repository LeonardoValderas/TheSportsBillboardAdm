package com.valdroide.thesportsbillboardinstitution.main_user.account

import android.content.Context

class AccountActivityInteractorImpl(val repository: AccountActivityRepository) : AccountActivityInteractor{

    override fun getAccount(context: Context) {
        repository.getAccount(context)
    }
}