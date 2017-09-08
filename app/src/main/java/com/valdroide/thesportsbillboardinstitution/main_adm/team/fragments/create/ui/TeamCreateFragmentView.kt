package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface TeamCreateFragmentView {
    fun onClickPhoto()
    fun onClickButtonSave()
    fun setTeamEdit(team: Team)
    fun fillViewUpdate()
    fun saveSuccess()
    fun editSuccess()
    fun cleanViews()
    fun setVisibilityViews(isVisible: Int)
    fun setError(error: String)
    fun hideProgressDialog()
    fun showProgressDialog()
}