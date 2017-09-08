package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface TeamUpdateFragmentInteractor {
    fun getTeams(context: Context)
    fun deleteTeam(context: Context, team: Team)
    fun activeUnActiveTeam(context: Context, team: Team)
}