package com.valdroide.thesportsbillboardadm.main.splash

import com.valdroide.thesportsbillboardadm.main.splash.events.SplashActivityEvent


interface SplashActivityInteractor {
    fun onCreate()
    fun onDestroy()
    fun OnEventMainThread(event: SplashActivityEvent)
}