package com.valdroide.thesportsbillboardinstitution.main.account.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Account


class AccountActivityEvent {
    open var type: Int = 0
    open var error: String? = null
    open var account: Account? = null
  //  open var dateData: DateData? = null

    companion object {
        const val ACCOUNT: Int = 0
        const val ERROR: Int =  1
    }
}