package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.events

import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

class SanctionCreateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var subMenuDrawers: MutableList<SubMenuDrawer>? = null
    var players: MutableList<Player>? = null
    var sanction: Sanction? = null

    companion object {
        const val GETSUBMENUPLAYER: Int = 0
        const val SAVESANCTION: Int = 1
        const val UPDATESANCTION: Int = 2
        const val GETSANCTION: Int = 3
        const val GETPLAYERFORID: Int = 4
        const val ERROR: Int = 5
    }
}