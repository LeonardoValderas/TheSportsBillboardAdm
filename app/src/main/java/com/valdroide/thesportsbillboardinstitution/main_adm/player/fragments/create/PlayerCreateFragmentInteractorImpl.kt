package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position

class PlayerCreateFragmentInteractorImpl(val repository: PlayerCreateFragmentRepository) : PlayerCreateFragmentInteractor {

    override fun getPlayer(context: Context, id_player: Int) {
        repository.getPlayer(context, id_player)
    }

    override fun savePlayer(context: Context, player: Player) {
        repository.savePlayer(context, player)
    }

    override fun updatePlayer(context: Context, player: Player) {
        repository.updatePlayer(context, player)
    }

    override fun savePosition(context: Context, postion: Position) {
        repository.savePosition(context, postion)
    }

    override fun updatePosition(context: Context, postion: Position) {
        repository.updatePosition(context, postion)
    }

    override fun getPositionsSubMenus(context: Context) {
        repository.getPositionsSubMenus(context)
    }
}