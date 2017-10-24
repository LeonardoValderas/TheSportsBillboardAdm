package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.events.TournamentActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

interface TournamentActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getSubMenuTournaments(context: Context)
    fun getSubMenuForId(context: Context, id_tournament: Tournament)
    fun saveTournament(context: Context, tournament: Tournament)
    fun activeOrUnActiveTournament(context: Context, tournament: Tournament)
    fun updateTournament(context: Context, tournament: Tournament)
    fun deleteTournament(context: Context, tournament: Tournament)
    fun assignationUnassignation(context: Context, subMenu: SubMenuDrawer, tounament: Int, isActual: Boolean)
    fun onEventMainThread(event: TournamentActivityEvent)
}