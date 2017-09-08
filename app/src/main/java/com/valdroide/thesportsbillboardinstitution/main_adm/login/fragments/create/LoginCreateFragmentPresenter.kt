package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.events.LoginCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui.LoginCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface LoginCreateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): LoginCreateFragmentView
    fun setViewPresenter(view: LoginCreateFragmentView)
    fun getLogin(context: Context, id_login: Int)
    fun saveLogin(context: Context, login: Login)
    fun editLogin(context: Context, login: Login)
    fun onEventMainThread(event: LoginCreateFragmentEvent)
}