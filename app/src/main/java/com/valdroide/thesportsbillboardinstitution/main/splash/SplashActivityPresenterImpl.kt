package com.valdroide.thesportsbillboardinstitution.main.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView

class SplashActivityPresenterImpl(val view: SplashActivityView, val event: EventBus, val interactor: SplashActivityInteractor) : SplashActivityPresenter {


    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getDateClub(context: Context) {
        interactor.getDateClub(context)
    }

    override fun validateDateClub(context: Context) {
        interactor.validateDateClub(context)
    }

    override fun getLogin(context: Context) {
        interactor.getLogin(context)
    }

    override fun validateLogin(context: Context, login: Login) {
        interactor.validateLogin(context, login)
    }

    override fun sendEmail(context: Context, comment: String) {
        interactor.sendEmail(context, comment)
    }

    override fun getSplashView(): SplashActivityView {
        return view
    }

    override fun onEventMainThread(event: SplashActivityEvent) {
        if (view != null) {
            when (event.type) {
                SplashActivityEvent.GOTOLOG -> {
                    view.hideDialogProgress()
                    view.goToLog()
                }
                SplashActivityEvent.ERROR -> {
                    view.hideDialogProgress()
                    view.setError(event.error)
                }
                SplashActivityEvent.GOTONAV -> {
                    view.hideDialogProgress()
                    view.goToNav()
                }
            }
        }
    }
}