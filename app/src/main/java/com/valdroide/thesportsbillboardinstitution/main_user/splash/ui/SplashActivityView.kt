package com.valdroide.thesportsbillboardinstitution.main_user.splash.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.DateData


interface SplashActivityView {
    fun hideDialogProgress()
    fun setError(error: String)
    fun goToNav()
    fun goToLog()
    fun setDate(dateData: DateData?)
}
