package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Login

class LoginEditFragmentInteractorImpl(val repository: LoginEditFragmentRepository) : LoginEditFragmentInteractor {

    override fun getLogins(context: Context) {
        repository.getLogins(context)
    }

    override fun activeOrUnActiveLogins(context: Context, login: Login) {
        repository.activeOrUnActiveLogins(context, login)
    }
}