package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.events.TeamCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui.TeamCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface TeamCreateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): TeamCreateFragmentView
    fun setViewPresenter(view: TeamCreateFragmentView)
    fun getTeam(context: Context, id_team: Int)
    fun saveTeam(context: Context, team: Team)
    fun updateTeam(context: Context, team: Team)
    fun onEventMainThread(event: TeamCreateFragmentEvent)
}