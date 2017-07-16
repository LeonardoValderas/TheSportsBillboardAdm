package com.valdroide.thesportsbillboardadm.main.splash

import com.valdroide.thesportsbillboardadm.lib.base.EventBus
import com.valdroide.thesportsbillboardadm.main.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardadm.main.splash.ui.SplashActivityView

class SplashActivityPresenterImpl(val view: SplashActivityView, val event: EventBus, val interactor: SplashActivityInteractor) : SplashActivityPresenter {

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun OnEventMainThread(event: SplashActivityEvent) {

        if(view != null){
            when(event.type) {
                SplashActivityEvent.GOTOLOG ->  view.hideDialogProgress()
                SplashActivityEvent.ERROR ->  view.hideDialogProgress()
                SplashActivityEvent.GOTONAV ->  view.hideDialogProgress()
            }
        }



    }
}