package com.valdroide.thesportsbillboardinstitution.main_adm.account.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Account

interface AccountAdmActivityView {
    fun onClickImageView()
    fun setAccount(account: Account)
    fun setError(error: String)
    fun getPhoto()
    fun saveSuccess()
    fun fillViews(account: Account)
    fun setEnableViews(enable: Boolean)
    fun menuVisibility(isSave: Boolean, isEdit: Boolean)
    fun cleanViews()
    fun hideProgressBar()
    fun showProgressBar()
}