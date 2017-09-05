package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Player

class PlayerFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var players: MutableList<Player>? = null

    companion object {
        const val PLAYERS: Int = 0
        const val ERROR: Int =  1
    }
}