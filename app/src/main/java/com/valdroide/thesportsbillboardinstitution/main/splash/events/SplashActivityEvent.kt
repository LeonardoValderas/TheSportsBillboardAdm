package com.valdroide.thesportsbillboardinstitution.main.splash.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Login

open class SplashActivityEvent {
    open var type: Int = 0
    open lateinit var error: String
    open lateinit var login: Login

    companion object {
        const val GOTOLOG: Int = 0;
        const val GOTONAV: Int =  1;
        const val ERROR: Int =  2;
    }
}
