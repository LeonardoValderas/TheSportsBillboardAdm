package com.valdroide.thesportsbillboardinstitution.main_user.tab.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament

class TabActivityEvent {

    var type: Int = 0
    var error: String? = null
    var tournaments: MutableList<Tournament>? = null

    companion object {
        val GET_TOURNAMENT = 0
        val ERROR = 1
    }
}