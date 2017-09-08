package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface LoginCreateFragmentInteractor {
    fun getLogin(context: Context, id_login: Int)
    fun saveLogin(context: Context, login: Login)
    fun editLogin(context: Context, login: Login)
}