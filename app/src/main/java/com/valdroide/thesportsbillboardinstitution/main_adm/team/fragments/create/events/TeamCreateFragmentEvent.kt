package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Team

class TeamCreateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var team: Team? = null

    companion object {
        const val SAVETEAM: Int = 0
        const val UPDATETEAM: Int = 1
        const val GETTEAM: Int = 2
        const val ERROR: Int =  3
    }
}