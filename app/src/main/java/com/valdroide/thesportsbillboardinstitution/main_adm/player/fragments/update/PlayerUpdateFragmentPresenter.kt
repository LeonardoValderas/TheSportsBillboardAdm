package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.events.PlayerUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.PlayerUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Player

interface PlayerUpdateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): PlayerUpdateFragmentView
    fun setViewPresenter(view: PlayerUpdateFragmentView)
    fun getPlayers(context: Context)
    fun activeUnActivePlayer(context: Context, player: Player)
    fun deletePlayer(context: Context, player: Player)
    fun onEventMainThread(event: PlayerUpdateFragmentEvent)
}