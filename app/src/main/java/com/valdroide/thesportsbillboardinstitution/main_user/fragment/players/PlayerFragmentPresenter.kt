package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.events.PlayerFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.PlayerFragmentView

interface PlayerFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): PlayerFragmentView
    fun setViewPresenter(view: PlayerFragmentView)
    fun getPlayers(context: Context, id_submenu: Int)
    fun onEventMainThread(event: PlayerFragmentEvent)
}