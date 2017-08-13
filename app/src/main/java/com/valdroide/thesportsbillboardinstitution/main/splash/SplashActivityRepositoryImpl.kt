package com.valdroide.thesportsbillboardinstitution.main.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus

class SplashActivityRepositoryImpl(val event: EventBus?, val apiService: ApiService?) : SplashActivityRepository {


    override fun getDateClub(context: Context) {

    }

    override fun validateDateClub(context: Context) {

    }

    override fun getLogin(context: Context) {

    }

    override fun validateLogin(context: Context, login: Login) {

    }

    override fun sendEmail(context: Context, comment: String) {

    }
}