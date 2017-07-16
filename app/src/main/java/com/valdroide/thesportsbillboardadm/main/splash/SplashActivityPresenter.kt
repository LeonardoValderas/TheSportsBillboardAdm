package com.valdroide.thesportsbillboardadm.main.splash

import com.valdroide.thesportsbillboardadm.main.splash.events.SplashActivityEvent


interface SplashActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun OnEventMainThread(event: SplashActivityEvent)
}