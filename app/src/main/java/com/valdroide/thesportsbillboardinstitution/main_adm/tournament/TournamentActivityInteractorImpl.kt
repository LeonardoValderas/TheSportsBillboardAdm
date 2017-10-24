package com.valdroide.thesportsbillboardinstitution.main_adm.tournament

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

class TournamentActivityInteractorImpl(val repository: TournamentActivityRepository) : TournamentActivityInteractor {

    override fun getSubMenuTournaments(context: Context) {
        repository.getSubMenuTournaments(context)
    }

    override fun getSubMenuForId(context: Context, id_tournament: Tournament) {
        repository.getSubMenuForId(context, id_tournament)
    }

    override fun saveTournament(context: Context, Tournament: Tournament) {
        repository.saveTournament(context, Tournament)
    }

    override fun updateTournament(context: Context, Tournament: Tournament) {
        repository.updateTournament(context, Tournament)
    }

    override fun activeOrUnActiveTournament(context: Context, tournament: Tournament) {
        repository.activeOrUnActiveTournament(context, tournament)
    }

    override fun deleteTournament(context: Context, Tournament: Tournament) {
        repository.deleteTournament(context, Tournament)
    }

    override fun assignationUnassignation(context: Context, subMenu: SubMenuDrawer, tounament: Int, isActual: Boolean) {
        repository.assignationUnassignation(context, subMenu, tounament, isActual)
    }
}