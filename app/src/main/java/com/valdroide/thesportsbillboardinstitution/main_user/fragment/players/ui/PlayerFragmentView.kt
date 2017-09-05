package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Player


interface PlayerFragmentView {
    fun setPlayers(players: MutableList<Player>)
    fun setError(error: String)
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}