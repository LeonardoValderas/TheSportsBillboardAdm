package com.valdroide.thesportsbillboardinstitution.main_user.account.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Account

interface AccountActivityView {
    fun setAccount(account: Account)
    fun setError(error: String)
    fun hideProgressBar()
    fun showProgressBar()
    fun onClickFabPhone()
    fun onClickFabEmail()
    fun onClickFabWeb()
    fun onClickFabFace()
    fun onClickFabInsta()

}