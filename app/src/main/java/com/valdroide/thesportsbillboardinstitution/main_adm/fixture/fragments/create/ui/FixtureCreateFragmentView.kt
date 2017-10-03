package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.*

interface FixtureCreateFragmentView {
    fun onClickButtonSave()
    fun setFixtureUpdate(Fixture: Fixture)
    fun setSpinnersData(submenus: MutableList<SubMenuDrawer>,
                        fieldMatch: MutableList<FieldMatch>,
                        timeMatch: MutableList<TimeMatch>,
                        tournament: MutableList<Tournament>,
                        team: MutableList<Team>)
    fun fillViewUpdate()
    fun saveSuccess()
    fun editSuccess()
    fun cleanViews()
    fun setVisibilityViews(isVisible: Int)
    fun setError(error: String)
    fun hideProgressDialog()
    fun showProgressDialog()
}