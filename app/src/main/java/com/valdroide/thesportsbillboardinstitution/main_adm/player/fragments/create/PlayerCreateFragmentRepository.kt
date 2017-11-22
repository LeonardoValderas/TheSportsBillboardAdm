package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position

interface PlayerCreateFragmentRepository {
    fun getPlayer(context: Context, id_player: Int)
    fun savePlayer(context: Context, player: Player)
    fun updatePlayer(context: Context, player: Player)
    fun savePosition(context: Context, postion: Position)
    fun updatePosition(context: Context, postion: Position)
    fun getPositionsSubMenus(context: Context)
}