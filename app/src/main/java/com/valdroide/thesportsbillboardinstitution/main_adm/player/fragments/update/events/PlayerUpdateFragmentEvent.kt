package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Team

class PlayerUpdateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var players: MutableList<Player>? = null

    companion object {
        const val PLAYERS: Int = 0
        const val UPDATE: Int =  1
        const val DELETE: Int =  2
        const val ERROR: Int =  3
    }
}