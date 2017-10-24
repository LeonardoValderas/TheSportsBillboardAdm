package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch


class FixtureUpdateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var fixtures: MutableList<Fixture>? = null
    var times: MutableList<TimeMatch>? = null
    var fixture: Fixture? = null

    companion object {
        const val FIXTURES: Int = 0
        const val UPDATE: Int = 1
        const val DELETE: Int =  2
        const val ERROR: Int =  3
    }
}