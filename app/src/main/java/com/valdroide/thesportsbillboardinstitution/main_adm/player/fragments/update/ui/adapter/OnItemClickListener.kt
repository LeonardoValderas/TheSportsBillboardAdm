package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter

import com.valdroide.thesportsbillboardinstitution.model.entities.Player


interface OnItemClickListener {
    fun onClickActivePlayer(position: Int, player: Player)
    fun onClickUnActivePlayer(position: Int, player: Player)
    fun onClickUpdatePlayer(position: Int, player: Player)
    fun onClickDeletePlayer(position: Int, player: Player)
}