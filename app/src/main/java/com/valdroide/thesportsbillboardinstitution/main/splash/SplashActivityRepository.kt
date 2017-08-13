package com.valdroide.thesportsbillboardinstitution.main.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface SplashActivityRepository {
    fun getDateClub(context: Context)
    fun validateDateClub(context: Context)
    fun getLogin(context: Context)
    fun validateLogin(context: Context, login: Login)
    fun sendEmail(context: Context, comment: String)
}