package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.Player


interface PlayerUpdateFragmentView {
    fun setAllPlayers(Players: MutableList<Player>)
    fun setError(error: String)
    fun updatePlayerSuccess()
    fun deletePlayerSuccess()
    fun hideSwipeRefreshLayout()
    fun showSwipeRefreshLayout()
}