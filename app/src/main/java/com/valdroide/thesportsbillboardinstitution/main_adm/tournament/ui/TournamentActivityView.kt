package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

interface TournamentActivityView {
    fun setSubMenusTournaments(tournament: MutableList<Tournament>, submenus: MutableList<SubMenuDrawer>)
    fun setError(error: String)
    fun eventSuccess(msg: String)
    fun refreshData()
    fun showProgressBar()
    fun hideProgressBar()
    fun assignationSuccess()
    fun setVisibilityViews(isVisible: Int)
    fun cleanViews()
    fun setTournamentForSubMenu(id: Int)
}