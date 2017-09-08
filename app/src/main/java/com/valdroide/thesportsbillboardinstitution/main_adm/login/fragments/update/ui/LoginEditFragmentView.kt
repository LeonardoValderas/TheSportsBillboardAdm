package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface LoginEditFragmentView {
    fun setAllLogins(logins: MutableList<Login>)
    fun setError(error: String)
    fun loginUpdate()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}