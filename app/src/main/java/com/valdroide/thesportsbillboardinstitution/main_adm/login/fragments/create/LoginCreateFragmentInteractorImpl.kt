package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

class LoginCreateFragmentInteractorImpl(val repository: LoginCreateFragmentRepository) : LoginCreateFragmentInteractor {


    override fun getLogin(context: Context, id_login: Int) {
        repository.getLogin(context, id_login)
    }

    override fun saveLogin(context: Context, login: Login) {
        repository.saveLogin(context, login)
    }

    override fun editLogin(context: Context, login: Login) {
        repository.editLogin(context, login)
    }
}