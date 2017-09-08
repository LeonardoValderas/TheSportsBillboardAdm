package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface LoginCreateFragmentView {
    fun setLoginEdit(login: Login)
    fun saveSuccess()
    fun editSuccess()
    fun cleanViews()
    fun setError(error: String)
    fun hideProgressDialog()
    fun showProgressDialog()
}