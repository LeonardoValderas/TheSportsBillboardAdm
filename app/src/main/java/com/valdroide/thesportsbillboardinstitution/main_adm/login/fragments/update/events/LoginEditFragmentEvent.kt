package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Login

class LoginEditFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var logins: MutableList<Login>? = null

    companion object {
        const val LOGINS: Int = 0
        const val EDIT: Int =  1
        const val DELETE: Int =  2
        const val ERROR: Int =  3
    }
}