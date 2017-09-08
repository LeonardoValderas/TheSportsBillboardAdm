package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

class TeamCreateFragmentInteractorImpl(val repository: TeamCreateFragmentRepository) : TeamCreateFragmentInteractor {


    override fun getTeam(context: Context, id_team: Int) {
        repository.getTeam(context, id_team)
    }

    override fun saveTeam(context: Context, team: Team) {
        repository.saveTeam(context, team)
    }

    override fun updateTeam(context: Context, team: Team) {
        repository.updateTeam(context, team)
    }
}