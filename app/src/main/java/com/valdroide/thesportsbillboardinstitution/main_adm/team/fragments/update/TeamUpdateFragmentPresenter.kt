package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.events.TeamUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface TeamUpdateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): TeamUpdateFragmentView
    fun setViewPresenter(view: TeamUpdateFragmentView)
    fun getTeams(context: Context)
    fun activeUnActiveTeam(context: Context, team: Team)
    fun deleteTeam(context: Context, team: Team)
    fun onEventMainThread(event: TeamUpdateFragmentEvent)
}