package com.valdroide.thesportsbillboardinstitution.main.splash.events

import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

open class SplashActivityEvent {
    open var type: Int = 0
    open var error: String? = null
    open var login: Login? = null
    open var dateData: DateData? = null

    companion object {
        const val GOTOLOG: Int = 0
        const val GOTONAV: Int =  1
        const val ERROR: Int =  2
        const val DATEDATA: Int =  3
    }
}
