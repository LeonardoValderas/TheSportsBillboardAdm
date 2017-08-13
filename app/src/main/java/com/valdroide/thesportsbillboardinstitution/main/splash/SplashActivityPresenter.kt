package com.valdroide.thesportsbillboardinstitution.main.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.main.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardinstitution.main.splash.ui.SplashActivityView

interface SplashActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getDateClub(context: Context)
    fun validateDateClub(context: Context)
    fun getLogin(context: Context)
    fun validateLogin(context: Context, login: Login)
    fun sendEmail(context: Context, comment: String)
    fun getSplashView(): SplashActivityView
    fun onEventMainThread(event: SplashActivityEvent)
}