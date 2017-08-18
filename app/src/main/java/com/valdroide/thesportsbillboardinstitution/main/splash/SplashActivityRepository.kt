package com.valdroide.thesportsbillboardinstitution.main.splash

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface SplashActivityRepository {
    fun getDate(context: Context)
    fun getData(context: Context)
    fun validateDate(context: Context, dateData: DateData)
    fun getLogin(context: Context)
    fun validateLogin(context: Context, login: Login)
    fun sendEmail(context: Context, comment: String)
}