package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.events.LoginEditFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface LoginEditFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): LoginEditFragmentView
    fun setViewPresenter(view: LoginEditFragmentView)
    fun getLogins(context: Context)
    fun activeOrUnActiveLogins(context: Context, login: Login)
    fun deleteLogin(context: Context, login: Login)
    fun onEventMainThread(event: LoginEditFragmentEvent)
}