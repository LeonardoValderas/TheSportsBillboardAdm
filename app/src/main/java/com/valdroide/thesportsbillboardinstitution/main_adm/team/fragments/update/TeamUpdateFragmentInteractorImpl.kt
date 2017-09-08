package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

class TeamUpdateFragmentInteractorImpl(val repository: TeamUpdateFragmentRepository) : TeamUpdateFragmentInteractor {

    override fun getTeams(context: Context) {
        repository.getTeams(context)
    }

    override fun activeUnActiveTeam(context: Context, team: Team) {
        repository.activeUnActiveTeam(context, team)
    }
    override fun deleteTeam(context: Context, team: Team) {
        repository.deleteTeam(context, team)
    }
}