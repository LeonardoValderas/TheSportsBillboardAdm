package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface LoginEditFragmentInteractor {
    fun getLogins(context: Context)
    fun activeOrUnActiveLogins(context: Context, login: Login)
 }