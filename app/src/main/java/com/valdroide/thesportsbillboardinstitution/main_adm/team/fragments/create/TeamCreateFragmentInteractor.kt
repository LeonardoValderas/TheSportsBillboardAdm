package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface TeamCreateFragmentInteractor {
    fun getTeam(context: Context, id_team: Int)
    fun saveTeam(context: Context, team: Team)
    fun updateTeam(context: Context, team: Team)
}