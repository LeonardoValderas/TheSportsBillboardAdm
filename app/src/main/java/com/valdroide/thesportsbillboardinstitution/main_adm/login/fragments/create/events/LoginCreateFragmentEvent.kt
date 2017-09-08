package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Login

class LoginCreateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var login: Login? = null

    companion object {
        const val SAVELOGIN: Int = 0
        const val EDITLOGIN: Int = 1
        const val GETLOGIN: Int = 2
        const val ERROR: Int =  3
    }
}