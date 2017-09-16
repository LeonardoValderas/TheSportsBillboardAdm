package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Player

interface PlayerUpdateFragmentInteractor {
    fun getPlayers(context: Context)
    fun deletePlayer(context: Context, player: Player)
    fun activeUnActivePlayer(context: Context, player: Player)
}