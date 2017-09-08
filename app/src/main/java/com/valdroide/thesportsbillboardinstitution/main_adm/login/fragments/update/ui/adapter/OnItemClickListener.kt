package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter

import com.valdroide.thesportsbillboardinstitution.model.entities.Login

interface OnItemClickListener {
    fun onClickEditLogin(position: Int, login: Login)
    fun onClickActiveLogin(position: Int, login: Login)
    fun onClickUnActiveLogin(position: Int, login: Login)
}
