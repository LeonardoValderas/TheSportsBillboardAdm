package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

interface TournamentActivityView {
    fun setSubMenusTournaments(tournament: MutableList<Tournament>)
    fun setSubMenusForTournament(subnMenus: MutableList<SubMenuDrawer>)
    fun setError(error: String)
    fun eventSuccess(msg: String)
    fun refreshRecyclerAndSpinner()
    fun showProgressBar()
    fun hideProgressBar()
    fun assignationSuccess(menuDrawer: SubMenuDrawer, isActual: Boolean)
    fun setVisibilityViews(isVisible: Int)
    fun cleanViews()
}