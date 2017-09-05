package com.valdroide.thesportsbillboardinstitution.main_user.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.main_user.splash.events.SplashActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_user.splash.ui.SplashActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData

interface SplashActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getDate(context: Context)
    fun getData(context: Context)
    fun validateDate(context: Context, dateData: DateData)
    fun getLogin(context: Context)
    fun validateLogin(context: Context, login: Login)
    fun sendEmail(context: Context, comment: String)
    fun getSplashView(): SplashActivityView
    fun onEventMainThread(event: SplashActivityEvent)
}