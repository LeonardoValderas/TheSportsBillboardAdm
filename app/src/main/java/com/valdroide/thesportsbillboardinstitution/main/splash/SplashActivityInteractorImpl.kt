package com.valdroide.thesportsbillboardinstitution.main.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

class SplashActivityInteractorImpl(val repository: SplashActivityRepository) : SplashActivityInteractor {


    override fun getDateClub(context: Context) {
        repository.getDateClub(context)
    }

    override fun validateDateClub(context: Context) {
        repository.validateDateClub(context)
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