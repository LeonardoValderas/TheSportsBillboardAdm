package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

class PlayerCreateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var player: Player? = null
    var position: Position? = null
    var positions: MutableList<Position>? = null
    var submenus: MutableList<SubMenuDrawer>? = null

    companion object {
        const val SAVEPLAYER: Int = 0
        const val UPDATEPLAYER: Int = 1
        const val GETPLAYER: Int = 2
        const val POSITIONSSUBMENUS: Int = 3
        const val SAVEPOSITION: Int = 4
        const val UPDATEPOSITION: Int = 5
        const val ERROR: Int =  6
    }
}