package com.valdroide.thesportsbillboardadm.main.splash.events

import com.valdroide.thesportsbillboardadm.entities.Login

class SplashActivityEvent {
    var type: Int = 0
    var error: String? = null
    var login: Login? = null

    companion object {
        const val GOTOLOG: Int = 0;
        const val GOTONAV: Int =  1;
        const val ERROR: Int =  2;
    }
}
