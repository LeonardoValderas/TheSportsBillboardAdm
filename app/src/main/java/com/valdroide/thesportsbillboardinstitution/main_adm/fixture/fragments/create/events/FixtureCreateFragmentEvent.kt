package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.events

import com.valdroide.thesportsbillboardinstitution.model.entities.*

class FixtureCreateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var subMenuDrawers: MutableList<SubMenuDrawer>? = null
    var timeMatchs: MutableList<TimeMatch>? = null
    var fieldMatchs: MutableList<FieldMatch>? = null
    var tournaments: MutableList<Tournament>? = null
    var teams: MutableList<Team>? = null
    var fixture: Fixture? = null

    companion object {
        const val GETSPINNERSDATA: Int = 0
        const val SAVEFIXTURE: Int = 1
        const val UPDATEFIXTURE: Int = 2
        const val GETFIXTURE: Int = 3
        const val ERROR: Int = 4
    }
}