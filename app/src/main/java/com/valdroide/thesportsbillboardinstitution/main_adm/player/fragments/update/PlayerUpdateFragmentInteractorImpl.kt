package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Player

class PlayerUpdateFragmentInteractorImpl(val repository: PlayerUpdateFragmentRepository) : PlayerUpdateFragmentInteractor {

    override fun getPlayers(context: Context) {
        repository.getPlayers(context)
    }

    override fun activeUnActivePlayer(context: Context, player: Player) {
        repository.activeUnActivePlayer(context, player)
    }
    override fun deletePlayer(context: Context, player: Player) {
        repository.deletePlayer(context, player)
    }
}