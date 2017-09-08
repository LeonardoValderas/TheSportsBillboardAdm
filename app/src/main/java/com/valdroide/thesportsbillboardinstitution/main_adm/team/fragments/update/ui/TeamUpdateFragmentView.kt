package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface TeamUpdateFragmentView {
    fun setAllTeams(teams: MutableList<Team>)
    fun setError(error: String)
    fun updateTeamSuccess()
    fun deleteTeamSuccess()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}