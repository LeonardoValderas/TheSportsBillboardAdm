package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Team

class TeamUpdateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var teams: MutableList<Team>? = null

    companion object {
        const val TEAMS: Int = 0
        const val UPDATE: Int =  1
        const val DELETE: Int =  2
        const val ERROR: Int =  3
    }
}