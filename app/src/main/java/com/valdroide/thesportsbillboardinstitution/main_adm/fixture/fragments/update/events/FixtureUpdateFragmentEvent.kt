package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture


class FixtureUpdateFragmentEvent {
    var type: Int = 0
    var error: String? = null
    var Fixtures: MutableList<Fixture>? = null

    companion object {
        const val FIXTURES: Int = 0
        const val DELETE: Int =  1
        const val ERROR: Int =  2
    }
}