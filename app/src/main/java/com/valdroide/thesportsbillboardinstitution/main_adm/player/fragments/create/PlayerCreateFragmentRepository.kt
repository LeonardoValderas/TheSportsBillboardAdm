package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Player

interface PlayerCreateFragmentRepository {
    fun getPlayer(context: Context, id_player: Int)
    fun savePlayer(context: Context, player: Player)
    fun updatePlayer(context: Context, player: Player)
    fun getPositionsSubMenus(context: Context)
}