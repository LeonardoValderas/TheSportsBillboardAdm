package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

interface TournamentActivityRepository {
    fun getSubMenuTournaments(context: Context)
    fun saveTournament(context: Context, Tournament: Tournament)
    fun updateTournament(context: Context, Tournament: Tournament)
    fun activeOrUnActiveTournament(context: Context, tournament: Tournament)
    fun deleteTournament(context: Context, Tournament: Tournament)
    fun assignationTournament(context: Context, subMenu: SubMenuDrawer, tounament: Int)
    fun getTournamentForSubMenu(context: Context, id_submenu: Int)
}