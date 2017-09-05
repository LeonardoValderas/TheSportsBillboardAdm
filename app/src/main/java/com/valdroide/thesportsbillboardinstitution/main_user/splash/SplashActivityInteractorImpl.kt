package com.valdroide.thesportsbillboardinstitution.main_user.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

class SplashActivityInteractorImpl(val repository: SplashActivityRepository) : SplashActivityInteractor {


    override fun getDate(context: Context) {
        repository.getDate(context)
    }

    override fun getData(context: Context) {
        repository.getData(context)
    }

    override fun validateDate(context: Context, dateData: DateData) {
        repository.validateDate(context, dateData)
    }

    override fun getLogin(context: Context) {
        repository.getLogin(context)
    }

    override fun validateLogin(context: Context, login: Login) {
        repository.validateLogin(context, login)
    }

    override fun sendEmail(context: Context, comment: String) {
    }
}