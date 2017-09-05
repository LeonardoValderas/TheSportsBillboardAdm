package com.valdroide.thesportsbillboardinstitution.main_user.account.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Account

/**
 var b: String? = "abc" signific that accept null, can call NPE
 b = null // ok
 b?.lenght - > it means that validate if is null. It's same to if(b != null)
 b?.length ?: -1  -> shot condition
 b!!.length doesn't realice a validation, so it can throw a NPE
 */

open class AccountActivityEvent {
    var type: Int = 0
    var error: String? = null
    var account: Account? = null
    companion object {
        const val ACCOUNT: Int = 0
        const val ERROR: Int =  1
    }
}