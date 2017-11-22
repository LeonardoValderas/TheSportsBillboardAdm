package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.events.PlayerCreateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.PlayerCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position

interface PlayerCreateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): PlayerCreateFragmentView
    fun setViewPresenter(view: PlayerCreateFragmentView)
    fun getPlayer(context: Context, id_team: Int)
    fun savePlayer(context: Context, player: Player)
    fun updatePlayer(context: Context, player: Player)
    fun savePosition(context: Context, postion: Position)
    fun updatePosition(context: Context, postion: Position)
    fun getPositionsSubMenus(context: Context)
    fun onEventMainThread(event: PlayerCreateFragmentEvent)
}