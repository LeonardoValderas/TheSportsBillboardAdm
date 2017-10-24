package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

interface TournamentActivityInteractor {
    fun getSubMenuTournaments(context: Context)
    fun getSubMenuForId(context: Context, id_tournament: Tournament)
    fun saveTournament(context: Context, Tournament: Tournament)
    fun updateTournament(context: Context, Tournament: Tournament)
    fun activeOrUnActiveTournament(context: Context, tournament: Tournament)
    fun deleteTournament(context: Context, Tournament: Tournament)
    fun assignationUnassignation(context: Context, subMenu: SubMenuDrawer, tounament: Int, isActual: Boolean)
}