package com.valdroide.thesportsbillboardadm.main.splash

import com.valdroide.thesportsbillboardadm.lib.base.EventBus
import com.valdroide.thesportsbillboardadm.main.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardadm.main.splash.ui.SplashActivityView

class SplashActivityInteractorImpl(val view: SplashActivityView, val event: EventBus) : SplashActivityPresenter {

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun OnEventMainThread(event: SplashActivityEvent) {
    }
}